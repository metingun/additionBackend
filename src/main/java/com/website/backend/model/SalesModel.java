package com.website.backend.model;

import javax.persistence.*;

@Entity
public class SalesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String salesStartDate;

    @Column
    private String salesFinishDate;

    @Column
    private String salesDate;

    @Column
    private String cancelSalesDate;

    @Column
    private long salesStartDateLong;

    @Column
    private long salesFinishDateLong;

    @Column
    private long salesDateLong;

    @Column
    private long cancelSalesDateLong;

    @Column
    private int productNo;

    @Column
    private String productName;

    @Column
    private int quantity;

    @Column
    private String categoryName;

    @Column
    private double unitPrice;

    @Column
    private double totalPrice;

    @Column
    private long additionNo;

    @Column
    private String tableName;

    @Column
    private int userNo;

    @Column
    private int orderStatus;

    @Column
    private int completeOrder;

    @Column
    private int menuType;

    @Column
    private String comment;

    @Column
    private int cancelSales;

    @Column
    private int cancelSalesCheck;

    @Column
    private int cancelUserNo;

    public String getSalesStartDate() {
        return salesStartDate;
    }

    public void setSalesStartDate(String salesStartDate) {
        this.salesStartDate = salesStartDate;
    }

    public String getSalesFinishDate() {
        return salesFinishDate;
    }

    public void setSalesFinishDate(String salesFinishDate) {
        this.salesFinishDate = salesFinishDate;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getCancelSalesDate() {
        return cancelSalesDate;
    }

    public void setCancelSalesDate(String cancelSalesDate) {
        this.cancelSalesDate = cancelSalesDate;
    }

    public long getSalesStartDateLong() {
        return salesStartDateLong;
    }

    public void setSalesStartDateLong(long salesStartDateLong) {
        this.salesStartDateLong = salesStartDateLong;
    }

    public long getSalesFinishDateLong() {
        return salesFinishDateLong;
    }

    public void setSalesFinishDateLong(long salesFinishDateLong) {
        this.salesFinishDateLong = salesFinishDateLong;
    }

    public long getSalesDateLong() {
        return salesDateLong;
    }

    public void setSalesDateLong(long salesDateLong) {
        this.salesDateLong = salesDateLong;
    }

    public long getCancelSalesDateLong() {
        return cancelSalesDateLong;
    }

    public void setCancelSalesDateLong(long cancelSalesDateLong) {
        this.cancelSalesDateLong = cancelSalesDateLong;
    }

    public int getCancelSalesCheck() {
        return cancelSalesCheck;
    }

    public void setCancelSalesCheck(int cancelSalesCheck) {
        this.cancelSalesCheck = cancelSalesCheck;
    }


    public int getCancelUserNo() {
        return cancelUserNo;
    }

    public void setCancelUserNo(int cancelUserNo) {
        this.cancelUserNo = cancelUserNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCancelSales() {
        return cancelSales;
    }

    public void setCancelSales(int cancelSales) {
        this.cancelSales = cancelSales;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getAdditionNo() {
        return additionNo;
    }

    public void setAdditionNo(long additionNo) {
        this.additionNo = additionNo;
    }

    public String getTableNo() {
        return tableName;
    }

    public void setTableNo(String tableNo) {
        this.tableName = tableNo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(int completeOrder) {
        this.completeOrder = completeOrder;
    }

    public SalesModel() {
        System.out.println(toString());
    }

}
