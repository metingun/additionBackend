package com.website.backend.model;

import javax.persistence.*;

@Entity
public class AdditionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int activity;

    @Column
    private String additionStartDate;

    @Column
    private String additionFinishDate;

    @Column
    private long additionStartDateLong;

    @Column
    private long additionFinishDateLong;

    @Column
    private double payment;

    @Column
    private String discountName;

    @Column
    private double discountedPayment;

    @Column
    private double creditCardPayment;

    @Column
    private double cashPayment;

    @Column
    private String tableName;

    public double getDiscountedPayment() {
        return discountedPayment;
    }

    public void setDiscountedPayment(double discountedPayment) {
        this.discountedPayment = discountedPayment;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getAdditionStartDate() {
        return additionStartDate;
    }

    public void setAdditionStartDate(String additionStartDate) {
        this.additionStartDate = additionStartDate;
    }

    public String getAdditionFinishDate() {
        return additionFinishDate;
    }

    public void setAdditionFinishDate(String additionFinishDate) {
        this.additionFinishDate = additionFinishDate;
    }

    public long getAdditionStartDateLong() {
        return additionStartDateLong;
    }

    public void setAdditionStartDateLong(long additionStartDateLong) {
        this.additionStartDateLong = additionStartDateLong;
    }

    public long getAdditionFinishDateLong() {
        return additionFinishDateLong;
    }

    public void setAdditionFinishDateLong(long additionFinishDateLong) {
        this.additionFinishDateLong = additionFinishDateLong;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public double getCreditCardPayment() {
        return creditCardPayment;
    }

    public void setCreditCardPayment(double creditCardPayment) {
        this.creditCardPayment = creditCardPayment;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }
}
