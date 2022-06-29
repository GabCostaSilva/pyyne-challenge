package com.pyyne.challenge.bank.core.adapter;

import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Bank2AccountSourceAdapterTest {
    @Autowired
    Bank2AccountSourceAdapter bank2AccountSourceAdapter;

    @Test
    void shouldReturnBankAccountsBalancesFromExternalBankAccountId() {
        Bank2AccountSource bank2AccountSource = new Bank2AccountSource();
        long accountId = 1L;
        Bank2AccountBalance bank2Balance = bank2AccountSource.getBalance(accountId);

        BankAccountBalance bankBalance = bank2AccountSourceAdapter.getAccountBalance(accountId);

        Assertions.assertThat(bankBalance).isNotNull();
        Assertions.assertThat(bankBalance.balance()).isEqualTo(bank2Balance.getBalance());
        Assertions.assertThat(bankBalance.currency()).isEqualTo(bank2Balance.getCurrency());
    }
}