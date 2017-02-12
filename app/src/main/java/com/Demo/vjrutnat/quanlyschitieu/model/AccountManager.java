package com.Demo.vjrutnat.quanlyschitieu.model;

/**
 * Created by VjrutNAT on 11/28/2016.
 */

public class AccountManager {
    private String nameAccount;
    private String moneyManager;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountManager(String nameAccount, String moneyManager) {
        this.nameAccount = nameAccount;
        this.moneyManager = moneyManager;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getMoneyManager() {
        return moneyManager;
    }

    public void setMoneyManager(String moneyManager) {
        this.moneyManager = moneyManager;
    }

}
