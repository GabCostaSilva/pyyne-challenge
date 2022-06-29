package com.pyyne.challenge.bank.service.wrapper;

import com.pyyne.challenge.bank.core.BankAccountSource;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BankAccountSourceWrapper implements BankAccountSource {
    @Override
    public List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public List<Double> getAccountsBalances(long accountId) {
        return Collections.singletonList(1.0);
    }

    @Override
    public String getAccountCurrency(long accountId) {
        return null;
    }
}
