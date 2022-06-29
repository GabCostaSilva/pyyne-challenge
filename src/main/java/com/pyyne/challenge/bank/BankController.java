package com.pyyne.challenge.bank;

import com.pyyne.challenge.bank.core.BankAccountBalance;
import com.pyyne.challenge.bank.application.wrapper.BankAccountSourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BankAccountBalance> printBalances() {
        System.out.println("Implement me to pull balance information from all available bank integrations and display them, one after the other.");

        return bankAccountSourceWrapper.getAccountBalances(1);
    }

    public void printTransactions() {
        System.out.println("Implement me to pull transactions from all available bank integrations and display them, one after the other.");
    }
}
