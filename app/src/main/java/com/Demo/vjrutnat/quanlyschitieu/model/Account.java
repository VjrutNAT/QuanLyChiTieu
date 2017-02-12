package com.Demo.vjrutnat.quanlyschitieu.model;

/**
 * Created by VjrutNAT on 12/2/2016.
 */

public class Account {
    private String title;
    private int currentMoney;
    int id;

    public Account(String title, int currentMoney) {
        this.title = title;
        this.currentMoney = currentMoney;
    }

    public Account(String title, int currentMoney, int id) {
        this.title = title;
        this.currentMoney = currentMoney;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
