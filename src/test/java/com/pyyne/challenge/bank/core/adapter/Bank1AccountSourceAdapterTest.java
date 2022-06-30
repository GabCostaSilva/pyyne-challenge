package com.pyyne.challenge.bank.core.adapter;

import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest
class Bank1AccountSourceAdapterTest {

    @MockBean
    Bank1AccountSource bank1AccountSource;
    Bank1AccountSourceAdapter bank1AccountSourceAdapter = new Bank1AccountSourceAdapter();
    final double EXPECTED_BALANCE = 215.5d;
    final long ACCOUNT_ID = 1L;
    final String EXPECTED_CURRENCY = "USD";

    @BeforeEach
    void startUp() {
        Mockito.when(bank1AccountSource.getAccountBalance(ACCOUNT_ID)).thenReturn(EXPECTED_BALANCE);
        Mockito.when(bank1AccountSource.getAccountCurrency(ACCOUNT_ID)).thenReturn(EXPECTED_CURRENCY);
    }

    @Test
    void shouldReturnBankAccountsBalancesFromExternalBankAccountId() {

        BankAccountBalance bankBalance = bank1AccountSourceAdapter.getAccountBalance(ACCOUNT_ID);

        Assertions.assertThat(bankBalance).isNotNull();
        Assertions.assertThat(bankBalance.balance()).isEqualTo(EXPECTED_BALANCE);
        Assertions.assertThat(bankBalance.currency()).isEqualTo(EXPECTED_CURRENCY);
    }

    @Test
    void shouldReturnTheAccountCurrency() {

        String currency = bank1AccountSourceAdapter.getAccountCurrency(ACCOUNT_ID);

        Assertions.assertThat(currency).isEqualTo(EXPECTED_CURRENCY);
    }

    @Test
    void shouldReturnBankAccountsTransactions() {
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        List<Bank1Transaction> expectedTransactions = List.of(new Bank1Transaction(100d, Bank1Transaction.TYPE_CREDIT, "Check deposit"),
                new Bank1Transaction(25.5d, Bank1Transaction.TYPE_DEBIT, "Debit card purchase"),
                new Bank1Transaction(225d, Bank1Transaction.TYPE_DEBIT, "Rent payment"));
        Mockito.when(bank1AccountSource.getTransactions(ACCOUNT_ID, fromDate, toDate)).thenReturn(expectedTransactions);

        List<BankAccountTransaction> transactions = bank1AccountSourceAdapter.getTransactions(ACCOUNT_ID, fromDate, toDate);

        String[] expectedTransactionTypes = expectedTransactions.stream()
                .map(t -> String.valueOf(t.getType()))
                .toArray(String[]::new);
        Double[] expectedTransactionsAmounts = expectedTransactions.stream()
                .map(Bank1Transaction::getAmount)
                .mapToDouble(Double::doubleValue)
                .boxed()
                .toArray(Double[]::new);
        String[] expectedTransactionTexts = expectedTransactions.stream()
                .map(Bank1Transaction::getText)
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