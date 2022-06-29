package com.pyyne.challenge.bank.core;

public class BankAccountBalance {

    private double balance;
    private String currency;

    public BankAccountBalance(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
