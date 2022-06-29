package com;


import com.pyyne.challenge.bank.BankController;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.application.wrapper.BankAccountSourceWrapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BankControllerTest {

    @Autowired
    BankController bankController;

    @Test
    void shouldListBalancesFromMultipleBankAccounts() {
        BankAccountBalance bank1Balance = new BankAccountBalance(215.5d, "USD");
        BankAccountBalance bank2Balance = new BankAccountBalance(512.5d, "USD");

        List<BankAccountBalance> bankAccountBalances = bankController.printBalances(1L);

        Assertions.assertThat(bankAccountBalances).isNotEmpty();
        Assertions.assertThat(bankAccountBalances).containsExactly(bank1Balance, bank2Balance);
    }
}
