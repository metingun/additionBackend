package com.website.backend.repository;

import com.website.backend.model.SalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepo extends JpaRepository<SalesModel, Long> {
    SalesModel findById(long id);

    List<SalesModel> findAllByAdditionNo(long additionNo);

    List<SalesModel> findAllByProductNoAndCancelSales(int productNo,int cancelSales);

    List<SalesModel> findAllBySalesStartDateLongGreaterThanEqualAndSalesStartDateLongLessThanEqualAndProductNoAndCancelSales(long firstDate, long secondDate,int productNo,int cancelSales);

    List<SalesModel> findAllByAdditionNoAndCancelSales(long additionNo,int cancelSales);

    List<SalesModel> findAllByCancelSalesDateLongGreaterThanEqualAndCancelSalesDateLongLessThanEqualAndCancelSalesCheck(long firstDate, long secondDate,int cancelSalesCheck);

    List<SalesModel> findAllByCompleteOrderAndOrderStatusAndCancelSales(int completeOrder,int orderStatus,int cancelSales);

    List<SalesModel> findAllByOrderStatusAndTableName(int orderStatus,String tableName);

    List<SalesModel> findAllByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(int completeOrder,int orderStatus,int cancelSales,String tableName);

    List<SalesModel> findAllBySalesStartDateLongGreaterThanEqualAndSalesStartDateLongLessThanEqualAndCompleteOrderAndOrderStatusAndCancelSales(long firstDate, long secondDate,int completeOrder,int orderStatus,int cancelSales);
}
