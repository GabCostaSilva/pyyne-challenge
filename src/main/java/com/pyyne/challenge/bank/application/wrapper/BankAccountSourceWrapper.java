package com.pyyne.challenge.bank.application.wrapper;

import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.application.adapter.BankAccountSourceAdapter;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BankAccountSourceWrapper {

    private final List<BankAccountSourceAdapter> bankAccountSourceAdapters;

    @Autowired
    public BankAccountSourceWrapper(List<BankAccountSourceAdapter> bankAccountSourceAdapters) {
        this.bankAccountSourceAdapters = bankAccountSourceAdapters;
    }

    public List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        return null;
    }

    public List<BankAccountBalance> getAccountBalances(long accountId) {
        return bankAccountSourceAdapters
                .stream()
                .map(bankAccountSource -> bankAccountSource.getAccountBalance(accountId))
                .toList();
    }
}
