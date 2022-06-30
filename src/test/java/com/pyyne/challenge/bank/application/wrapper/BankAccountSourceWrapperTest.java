package com.pyyne.challenge.bank.application.wrapper;

import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import com.pyyne.challenge.bank.core.adapter.Bank1AccountSourceAdapter;
import com.pyyne.challenge.bank.core.adapter.Bank2AccountSourceAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class BankAccountSourceWrapperTest {

    Bank1AccountSource bank1AccountSource = Mockito.mock(Bank1AccountSource.class);

    Bank2AccountSource bank2AccountSource = Mockito.mock(Bank2AccountSource.class);

    @MockBean
    Bank1AccountSourceAdapter bank1AccountSourceAdapter;

    @MockBean
    Bank2AccountSourceAdapter bank2AccountSourceAdapter;

    @Autowired
    BankAccountSourceWrapper bankAccountSourceWrapper;

    @Test
    void shouldGetAllAccountsBalances() {
        long accountId = 1L;
        double expectedBank1BalanceValue = 525d;
        String expectedBank1Currency = "USD";
        double expectedBank2BalanceValue = 212.69d;
        String expectedBank2Currency = "USD";
        BankAccountBalance expectedBank1Balance = new BankAccountBalance(expectedBank1BalanceValue, expectedBank1Currency);
        Mockito.when(bank1AccountSourceAdapter.getAccountBalance(accountId)).thenReturn(expectedBank1Balance);
        Mockito.when(bank1AccountSourceAdapter.getAccountCurrency(accountId)).thenReturn(expectedBank1Currency);
        BankAccountBalance expectedBank2Balance = new BankAccountBalance(expectedBank2BalanceValue, expectedBank2Currency);
        Mockito.when(bank2AccountSourceAdapter.getAccountBalance(accountId)).thenReturn(expectedBank2Balance);
        Mockito.when(bank2AccountSourceAdapter.getAccountCurrency(accountId)).thenReturn(expectedBank2Currency);

        List<BankAccountBalance> accountBalances = bankAccountSourceWrapper.getAccountBalances(accountId);

        Assertions.assertThat(accountBalances).isNotNull();
        Assertions.assertThat(accountBalances).containsExactly(expectedBank1Balance, expectedBank2Balance);
    }

    @Test
    void shouldGetAllAccountsTransactions() {
        long accountId = 1L;
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        List<Bank1Transaction> expectedTransactionsFromBank1 = List.of(
                new Bank1Transaction(100d, Bank1Transaction.TYPE_CREDIT, "Check deposit"),
                new Bank1Transaction(25.5d, Bank1Transaction.TYPE_DEBIT, "Debit card purchase"),
                new Bank1Transaction(225d, Bank1Transaction.TYPE_DEBIT, "Rent payment"));
        List<Bank2AccountTransaction> expectedTransactionsFromBank2 = List.of(
                new Bank2AccountTransaction(125d, Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT, "Amazon.com"),
                new Bank2AccountTransaction(500d, Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT, "Car insurance"),
                new Bank2AccountTransaction(800d, Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT, "Salary"));
        List<BankAccountTransaction> expetedBankAccountTransactions = Stream.concat(
                        getBankAccountTransactionsStreamForBank1(expectedTransactionsFromBank1),
                        getBankAccountTransactionStreamForBank2(expectedTransactionsFromBank2))
                .toList();
        Mockito.when(bank1AccountSourceAdapter.getTransactions(accountId, fromDate, toDate))
                .thenReturn(getBankAccountTransactionsStreamForBank1(expectedTransactionsFromBank1).toList());
        Mockito.when(bank2AccountSourceAdapter.getTransactions(accountId, fromDate, toDate))
                .thenReturn(getBankAccountTransactionStreamForBank2(expectedTransactionsFromBank2).toList());

        List<BankAccountTransaction> transactions = bankAccountSourceWrapper.getTransactions(accountId, fromDate, toDate);

        Assertions.assertThat(transactions).containsExactly(expetedBankAccountTransactions.toArray(BankAccountTransaction[]::new));
    }

    private Stream<BankAccountTransaction> getBankAccountTransactionStreamForBank2(List<Bank2AccountTransaction> expectedTransactionsFromBank2) {
        return expectedTransactionsFromBank2
                .stream()
                .map(t -> new BankAccountTransaction(t.getAmount(), t.getType().name(), t.getText()));
    }

    private Stream<BankAccountTransaction> getBankAccountTransactionsStreamForBank1(List<Bank1Transaction> expectedTransactionsFromBank1) {
        return expectedTransactionsFromBank1
                .stream()
                .map(t -> new BankAccountTransaction(t.getAmount(), String.valueOf(t.getType()), t.getText()));
    }
}
