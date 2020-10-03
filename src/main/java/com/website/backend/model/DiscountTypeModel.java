package com.website.backend.model;

import javax.persistence.*;

@Entity
public class DiscountTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String discountName;

    @Column
    private double discountRate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return "DiscountTypeModel{" +
                "id=" + id +
                ", discountName='" + discountName + '\'' +
                ", discountRate=" + discountRate +
                '}';
    }
}
