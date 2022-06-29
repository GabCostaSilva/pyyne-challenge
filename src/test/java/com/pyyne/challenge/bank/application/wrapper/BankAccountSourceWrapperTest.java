package com.pyyne.challenge.bank.application.wrapper;

import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.application.wrapper.BankAccountSourceWrapper;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BankAccountSourceWrapperTest {

    @Autowired
    BankAccountSourceWrapper bankAccountSourceWrapper;

    @Test
    void shouldGetAllAccountsBalances() {
        long accountId = 1L;
        Bank1AccountSource bank1AccountSource = new Bank1AccountSource();
        Bank2AccountBalance bank2AccountBalance = new Bank2AccountSource().getBalance(accountId);
        BankAccountBalance expectedBank1Balance = new BankAccountBalance(bank1AccountSource.getAccountBalance(accountId),
                bank1AccountSource.getAccountCurrency(accountId));
        BankAccountBalance expectedBank2Balance = new BankAccountBalance(bank2AccountBalance.getBalance(), bank2AccountBalance.getCurrency());

        List<BankAccountBalance> accountBalances = bankAccountSourceWrapper.getAccountBalances(accountId);

        Assertions.assertThat(accountBalances).isNotNull();
        Assertions.assertThat(accountBalances).containsExactly(expectedBank1Balance, expectedBank2Balance);
    }
}
