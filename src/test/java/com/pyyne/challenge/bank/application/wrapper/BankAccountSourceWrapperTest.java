package com.pyyne.challenge.bank.application.wrapper;

import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.application.wrapper.BankAccountSourceWrapper;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BankAccountSourceWrapperTest {

    @Autowired
    BankAccountSourceWrapper bankAccountSourceWrapper;
    Bank1AccountSource bank1AccountSource = new Bank1AccountSource();
    Bank2AccountSource bank2AccountSource = new Bank2AccountSource();
    @Test
    void shouldGetAllAccountsBalances() {
        long accountId = 1L;
        Bank2AccountBalance bank2AccountBalance = bank2AccountSource.getBalance(accountId);
        BankAccountBalance expectedBank1Balance = new BankAccountBalance(bank1AccountSource.getAccountBalance(accountId),
                bank1AccountSource.getAccountCurrency(accountId));
        BankAccountBalance expectedBank2Balance = new BankAccountBalance(bank2AccountBalance.getBalance(), bank2AccountBalance.getCurrency());

        List<BankAccountBalance> accountBalances = bankAccountSourceWrapper.getAccountBalances(accountId);

        Assertions.assertThat(accountBalances).isNotNull();
        Assertions.assertThat(accountBalances).containsExactly(expectedBank1Balance, expectedBank2Balance);
    }

    @Test
    void shouldGetAllAccountsTransactions() {
        long accountId = 1L;
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        List<BankAccountTransaction> expectedTransactions = new java.util.ArrayList<>(bank1AccountSource
                .getTransactions(accountId, fromDate, toDate)
                .stream()
                .map(t -> new BankAccountTransaction(t.getAmount(), String.valueOf(t.getType()), t.getText()))
                .toList());
        expectedTransactions.addAll(bank2AccountSource.getTransactions(accountId, fromDate, toDate)
                .stream()
                .map(t -> new BankAccountTransaction(t.getAmount(), t.getType().name(), t.getText()))
                .toList());

        List<BankAccountTransaction> transactions = bankAccountSourceWrapper.getTransactions(accountId, fromDate, toDate);

        Assertions.assertThat(transactions).containsExactly(expectedTransactions.toArray(BankAccountTransaction[]::new));
    }
}
