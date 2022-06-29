package com.pyyne.challenge.bank.application.adapter;

import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.core.BankAccountTransaction;

import java.util.Date;
import java.util.List;

public interface BankAccountSourceAdapter {

    List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate);
    BankAccountBalance getAccountBalance(long accountId);
    String getAccountCurrency(long accountId);
}
