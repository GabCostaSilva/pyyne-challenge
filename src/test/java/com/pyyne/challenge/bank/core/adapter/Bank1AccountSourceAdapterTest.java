package com.pyyne.challenge.bank.core.adapter;

import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.bank2.integration.Bank2AccountTransaction;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import com.pyyne.challenge.bank.core.adapter.bank_source.Bank1AccountSourceAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest
class Bank1AccountSourceAdapterTest {

    @Autowired
    Bank1AccountSourceAdapter bank1AccountSourceAdapter;
    Bank1AccountSource bank1AccountSource = new Bank1AccountSource();

    @Test
    void shouldReturnBankAccountsBalancesFromExternalBankAccountId() {
        long accountId = 1L;
        Double accountBalance = bank1AccountSource.getAccountBalance(accountId);
        String accountCurrency = bank1AccountSource.getAccountCurrency(accountId);

        BankAccountBalance bankBalance = bank1AccountSourceAdapter.getAccountBalance(accountId);

        Assertions.assertThat(bankBalance).isNotNull();
        Assertions.assertThat(bankBalance.balance()).isEqualTo(accountBalance);
        Assertions.assertThat(bankBalance.currency()).isEqualTo(accountCurrency);
    }

    @Test
    void shouldReturnBankAccountsTransactions() {
        long accountId = 1L;
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        List<Bank1Transaction> expectedTransactions = bank1AccountSource.getTransactions(accountId, fromDate, toDate);

        List<BankAccountTransaction> transactions = bank1AccountSourceAdapter.getTransactions(accountId, fromDate, toDate);

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