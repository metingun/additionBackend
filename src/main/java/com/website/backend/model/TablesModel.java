package com.website.backend.model;

import javax.persistence.*;

@Entity
public class TablesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String tableName;

    @Column
    private String tableType;

    @Column
    private double payment;

    @Column
    private int menuType;

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public TablesModel() {
        System.out.println(toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "TablesModel{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", payment=" + payment +
                ", menuType=" + menuType +
                '}';
    }
}
