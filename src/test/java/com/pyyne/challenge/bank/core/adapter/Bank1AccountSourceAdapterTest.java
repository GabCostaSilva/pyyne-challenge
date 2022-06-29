package com.pyyne.challenge.bank.core.adapter;

import com.bank1.integration.Bank1AccountSource;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Bank1AccountSourceAdapterTest {

    @Autowired
    Bank1AccountSourceAdapter bank1AccountSourceAdapter;

    @Test
    void shouldReturnBankAccountsBalancesFromExternalBankAccountId() {
        Bank1AccountSource bank1AccountSource = new Bank1AccountSource();
        long accountId = 1L;
        Double accountBalance = bank1AccountSource.getAccountBalance(accountId);
        String accountCurrency = bank1AccountSource.getAccountCurrency(accountId);

        BankAccountBalance bankBalance = bank1AccountSourceAdapter.getAccountBalance(accountId);

        Assertions.assertThat(bankBalance).isNotNull();
        Assertions.assertThat(bankBalance.balance()).isEqualTo(accountBalance);
        Assertions.assertThat(bankBalance.currency()).isEqualTo(accountCurrency);
    }

}