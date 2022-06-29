package com.pyyne.challenge.bank.core;

import java.util.Date;
import java.util.List;

public interface BankAccountSource {

    List<BankAccountTransaction> getTransactions(long accountId, Date fromDate, Date toDate);
    List<Double> getAccountsBalances(long accountId);
    String getAccountCurrency(long accountId);
}
