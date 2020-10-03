package com.website.backend.model;

import javax.persistence.*;

@Entity
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int productNo;

    @Column
    private String productName;

    @Column
    private long categoryId;

    @Column
    private String categoryName;

    @Column
    private double unitPriceForIn;

    @Column
    private double unitPriceForOut;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPriceForIn() {
        return unitPriceForIn;
    }

    public void setUnitPriceForIn(double unitPriceForIn) {
        this.unitPriceForIn = unitPriceForIn;
    }

    public double getUnitPriceForOut() {
        return unitPriceForOut;
    }

    public void setUnitPriceForOut(double unitPriceForOut) {
        this.unitPriceForOut = unitPriceForOut;
    }

    public ProductModel() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", productNo=" + productNo +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", unitPriceForIn=" + unitPriceForIn +
                ", unitPriceForOut=" + unitPriceForOut +
                '}';
    }
}
