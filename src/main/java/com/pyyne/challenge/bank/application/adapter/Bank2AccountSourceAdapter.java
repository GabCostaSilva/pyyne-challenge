package com.pyyne.challenge.bank.application.adapter;

import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("bank2AccountSourceAdapter")
public class Bank2AccountSourceAdapter implements BankAccountSourceAdapter {
    private final Bank2AccountSource bank2AccountSource;

    public Bank2AccountSourceAdapter() {
        bank2AccountSource = new Bank2AccountSource();
    }

    @Override
    public List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public BankAccountBalance getAccountBalance(long accountId) {
        Bank2AccountBalance balance = bank2AccountSource.getBalance(accountId);
        return new BankAccountBalance(balance.getBalance(), balance.getCurrency());
    }

    @Override
    public String getAccountCurrency(long accountId) {
        return null;
    }
}
