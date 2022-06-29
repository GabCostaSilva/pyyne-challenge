package com.pyyne.challenge.bank.service.wrapper;

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
        BankAccountBalance bank1Balance = new BankAccountBalance(215.5d, "USD");
        BankAccountBalance bank2Balance = new BankAccountBalance(512.5d, "USD");

        List<BankAccountBalance> accountBalances = bankAccountSourceWrapper.getAccountBalances(1);

        Assertions.assertThat(accountBalances).isNotNull();
        Assertions.assertThat(accountBalances).containsExactly(bank1Balance, bank2Balance);
    }
}
