package com.Demo.vjrutnat.quanlyschitieu.model;

/**
 * Created by VjrutNAT on 12/2/2016.
 */

public class Static {
    private String Date, Time, Content, Surplus, Account, Transaction_type,Money;

    public Static(String date, String time, String content, String surplus, String account, String transaction_type, String money) {
        Date = date;
        Time = time;
        Content = content;
        Surplus = surplus;
        Account = account;
        Transaction_type = transaction_type;
        Money = money;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSurplus() {
        return Surplus;
    }

    public void setSurplus(String surplus) {
        Surplus = surplus;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getTransaction_type() {
        return Transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        Transaction_type = transaction_type;
    }
}
