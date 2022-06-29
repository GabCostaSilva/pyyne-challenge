package com.pyyne.challenge.bank.core;

import com.bank1.integration.Bank1AccountSource;

public record BankAccountBalance(double balance, String currency) {
    public BankAccountBalance(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }
}
