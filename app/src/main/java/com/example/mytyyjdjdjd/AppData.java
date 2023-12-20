package com.example.mytyyjdjdjd;

public class AppData {
    private static AppData instance;
    private float balance;
    private float income;
    private float itemPrice;
    private float itemCount;

    private float incomePerSecond;

    private AppData() {
        balance = 0f;
        income = 1f;
        itemPrice = 100f;
        itemCount = 1f;
    }

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getItemCount() {
        return itemCount;
    }

    public void setItemCount(float itemCount) {
        this.itemCount = itemCount;
    }
    public float getIncomePerSecond() {
        return incomePerSecond;
    }

    public void setIncomePerSecond(float incomePerSecond) {
        this.incomePerSecond = incomePerSecond;
    }
}