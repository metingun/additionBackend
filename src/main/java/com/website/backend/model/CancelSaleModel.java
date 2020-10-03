package com.website.backend.model;

import javax.persistence.*;

public class CancelSaleModel {
    private long saleId;
    private String comment;
    private int userNo;

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "CancelSaleModel{" +
                "saleId=" + saleId +
                ", comment='" + comment + '\'' +
                ", userNo=" + userNo +
                '}';
    }
}
