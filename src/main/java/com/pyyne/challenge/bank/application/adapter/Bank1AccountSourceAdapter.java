package com.pyyne.challenge.bank.application.adapter;

import com.bank1.integration.Bank1AccountSource;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("bank1AccountSourceAdapter")
public class Bank1AccountSourceAdapter implements BankAccountSourceAdapter {
    private final Bank1AccountSource bank1AccountSource;

    public Bank1AccountSourceAdapter() {
        bank1AccountSource = new Bank1AccountSource();
    }

    @Override
    public List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public BankAccountBalance getAccountBalance(long accountId) {
        return new BankAccountBalance(bank1AccountSource.getAccountBalance(accountId),
                bank1AccountSource.getAccountCurrency(accountId));
    }

    @Override
    public String getAccountCurrency(long accountId) {
        return bank1AccountSource.getAccountCurrency(accountId);
    }
}
