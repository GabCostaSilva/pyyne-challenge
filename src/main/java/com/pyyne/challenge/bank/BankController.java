package com.pyyne.challenge.bank;

import com.pyyne.challenge.bank.core.BankAccountBalance;

import java.util.Collections;
import java.util.List;

/**
 * Controller that pulls information form multiple bank integrations and prints them to the console.
 * <p>
 * Created by Par Renyard on 5/12/21.
 */
public class BankController {

    public List<BankAccountBalance> printBalances() {
        System.out.println("Implement me to pull balance information from all available bank integrations and display them, one after the other.");

        return Collections.singletonList(null);
    }

    public void printTransactions() {
        System.out.println("Implement me to pull transactions from all available bank integrations and display them, one after the other.");
    }
}
