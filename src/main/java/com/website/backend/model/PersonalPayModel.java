package com.website.backend.model;

import java.util.HashMap;

public class PersonalPayModel {
    private long personalId;
    private String personalName;
    private double personalSalary;
    private double remainingMoney;

    public long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(long personalId) {
        this.personalId = personalId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public double getPersonalSalary() {
        return personalSalary;
    }

    public void setPersonalSalary(double personalSalary) {
        this.personalSalary = personalSalary;
    }

    public double getRemainingMoney() {
        return remainingMoney;
    }

    public void setRemainingMoney(double remainingMoney) {
        this.remainingMoney = remainingMoney;
    }
}
