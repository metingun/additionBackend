package com.website.backend.model;

public class DailyIncomeModel {
    private double dailyIncome;
    private double cashIncome;
    private double creditCardIncome;
    private double totalExpense;
    private double safeTotal;
    private double totalProfit;

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public double getCashIncome() {
        return cashIncome;
    }

    public void setCashIncome(double cashIncome) {
        this.cashIncome = cashIncome;
    }

    public double getCreditCardIncome() {
        return creditCardIncome;
    }

    public void setCreditCardIncome(double creditCardIncome) {
        this.creditCardIncome = creditCardIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getSafeTotal() {
        return safeTotal;
    }

    public void setSafeTotal(double safeTotal) {
        this.safeTotal = safeTotal;
    }
}
