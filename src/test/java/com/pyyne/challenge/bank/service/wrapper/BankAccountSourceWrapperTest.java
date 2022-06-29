package com.pyyne.challenge.bank.service.wrapper;

import com.pyyne.challenge.bank.core.BankAccountSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BankAccountSourceWrapperTest {

    @Autowired
    BankAccountSource bankAccountSourceWrapper;

    @Test
    void shouldGetAllAccountsBalances() {
        List<Double> accountBalances = bankAccountSourceWrapper.getAccountsBalances(1);

        Assertions.assertThat(accountBalances).isNotNull();
        Assertions.assertThat(accountBalances).isNotEmpty();
    }
}