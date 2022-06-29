package com;


import com.pyyne.challenge.bank.BankController;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BankControllerTest {

    BankController bankController = new BankController();

    @Test
    void shouldListBalancesFromMultipleBankAccounts() {

        List<BankAccountBalance> bankAccountBalances = bankController.printBalances();

        Assertions.assertThat(bankAccountBalances).isNotEmpty();
        Assertions.assertThat(bankAccountBalances.get(0)).isNotNull();
    }
}
