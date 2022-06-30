package com.pyyne.challenge.bank.core.adapter;

import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest
class Bank2AccountSourceAdapterTest {
    @Autowired
    Bank2AccountSourceAdapter bank2AccountSourceAdapter;
    Bank2AccountSource bank2AccountSource = Mockito.mock(Bank2AccountSource.class);

    @Test
    void shouldReturnBankAccountsBalancesFromExternalBankAccountId() {
        long accountId = 1L;
        Bank2AccountBalance bank2Balance = Mockito.mock(Bank2AccountBalance.class);
        Double expectedBalance = 512.5d;
        String expectedCurrency = "USD";
        Mockito.when(bank2Balance.getBalance()).thenReturn(expectedBalance);
        Mockito.when(bank2Balance.getCurrency()).thenReturn(expectedCurrency);
        Mockito.when(bank2AccountSource.getBalance(accountId)).thenReturn(bank2Balance);

        BankAccountBalance bankBalance = bank2AccountSourceAdapter.getAccountBalance(accountId);

        Assertions.assertThat(bankBalance).isNotNull();
        Assertions.assertThat(bankBalance.balance()).isEqualTo(expectedBalance);
        Assertions.assertThat(bankBalance.currency()).isEqualTo(expectedCurrency);
    }

    @Test
    void shouldReturnBankAccountsTransactions() {
        long accountId = 1L;
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        List<Bank2AccountTransaction> expectedTransactions = List.of(new Bank2AccountTransaction(125d, Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT, "Amazon.com"),
                new Bank2AccountTransaction(500d, Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT, "Car insurance"),
                new Bank2AccountTransaction(800d, Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT, "Salary"));
        Mockito.when(bank2AccountSource.getTransactions(accountId, fromDate, toDate)).thenReturn(expectedTransactions);

        List<BankAccountTransaction> transactions = bank2AccountSourceAdapter.getTransactions(accountId, fromDate, toDate);

        String[] expectedTransactionTypes = expectedTransactions.stream()
                .map(bank2AccountTransaction -> bank2AccountTransaction.getType().name())
                .toArray(String[]::new);
        Double[] expectedTransactionsAmounts = expectedTransactions.stream()
                .map(Bank2AccountTransaction::getAmount)
                .mapToDouble(Double::doubleValue)
                .boxed()
                .toArray(Double[]::new);
        String[] expectedTransactionTexts = expectedTransactions.stream()
                .map(Bank2AccountTransaction::getText)
                .toArray(String[]::new);
        Assertions.assertThat(transactions).isNotEmpty();
        Assertions.assertThat(transactions)
                .map(BankAccountTransaction::transactionType)
                .containsExactly(expectedTransactionTypes);
        Assertions.assertThat(transactions)
                .map(BankAccountTransaction::amount)
                .containsExactly(expectedTransactionsAmounts);
        Assertions.assertThat(transactions)
                .map(BankAccountTransaction::text)
                .containsExactly(expectedTransactionTexts);

    }
}