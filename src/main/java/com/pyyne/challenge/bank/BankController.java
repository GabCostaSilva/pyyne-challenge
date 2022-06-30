package com.pyyne.challenge.bank;

import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.application.wrapper.BankAccountSourceWrapper;
import com.pyyne.challenge.bank.core.BankAccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Controller that pulls information form multiple bank integrations and prints them to the console.
 * <p>
 * Created by Par Renyard on 5/12/21.
 */
@Service
public class BankController {

    private final BankAccountSourceWrapper bankAccountSourceWrapper;
    @Autowired
    public BankController(BankAccountSourceWrapper bankAccountSourceWrapper) {
        this.bankAccountSourceWrapper = bankAccountSourceWrapper;
    }

    public List<BankAccountBalance> printBalances(Long accountId) {
        System.out.println("Implement me to pull balance information from all available bank integrations and display them, one after the other.");

        List<BankAccountBalance> accountBalances = bankAccountSourceWrapper.getAccountBalances(accountId);

        System.out.println(accountBalances);

        return accountBalances;
    }

    public List<BankAccountTransaction> printTransactions(long accountId, Date fromDate, Date toDate) {
        System.out.println("Implement me to pull transactions from all available bank integrations and display them, one after the other.");

        List<BankAccountTransaction> bankAccountsTransactions = bankAccountSourceWrapper.getTransactions(accountId, fromDate, toDate);

        System.out.println(bankAccountsTransactions);

        return bankAccountsTransactions;
    }
}
