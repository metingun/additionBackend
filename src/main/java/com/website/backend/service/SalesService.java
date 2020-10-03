package com.website.backend.service;

import com.website.backend.model.*;
import com.website.backend.repository.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesService {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private final SalesRepo salesRepo;
    private final ProductRepo productRepo;
    private final TablesRepo tablesRepo;
    private final AdditionRepo additionRepo;
    private final AdditionService additionService;
    private final CategoriesRepo categoriesRepo;

    public SalesService(SalesRepo salesRepo, ProductRepo productRepo, TablesRepo tablesRepo, AdditionRepo additionRepo,AdditionService additionService,CategoriesRepo categoriesRepo) {
        this.salesRepo = salesRepo;
        this.productRepo = productRepo;
        this.tablesRepo = tablesRepo;
        this.additionRepo = additionRepo;
        this.additionService = additionService;
        this.categoriesRepo = categoriesRepo;
    }

    public List<SalesModel> save(List<SalesModel> sales) throws ParseException {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        int menuType=tablesRepo.findByTableName(sales.get(0).getTableName()).getMenuType();
        for (SalesModel sale : sales) {
            ProductModel productModel = productRepo.findByProductNo(sale.getProductNo());
            if (menuType==0) {
                sale.setUnitPrice(productModel.getUnitPriceForIn());
            } else {
                sale.setUnitPrice(productModel.getUnitPriceForOut());
            }
            sale.setMenuType(menuType);
            sale.setSalesDate(nowDate);
            sale.setSalesStartDateLong(date.getTime());
            sale.setProductName(productModel.getProductName());
            sale.setCategoryName(productModel.getCategoryName());
            sale.setTotalPrice(sale.getUnitPrice() * sale.getQuantity());
            sale.setOrderStatus(1);
            sale.setCompleteOrder(0);
        }
        updateAdditionAndTable(sales);
        for (SalesModel sale : sales) {
            sale.setAdditionNo(additionRepo.findByTableNameAndActivity(sale.getTableName(), 1).getId());
        }
        salesRepo.saveAll(sales);


        return sales;
    }

    public void updateAdditionAndTable(List<SalesModel> sales) throws ParseException {
        AdditionModel additionModel;
        if (tablesRepo.findByTableName(sales.get(0).getTableName()).getPayment() == 0) {
            additionModel = new AdditionModel();
            additionModel.setTableName(sales.get(0).getTableName());
            additionModel.setActivity(1);
            additionModel.setPayment(sales.stream().mapToDouble(SalesModel::getTotalPrice).sum());
        } else {
            additionModel = additionRepo.findByTableNameAndActivity(sales.get(0).getTableName(), 1);
            additionModel.setPayment(sales.stream().mapToDouble(SalesModel::getTotalPrice).sum() + additionModel.getPayment());
        }
        additionModel.setAdditionStartDate(sales.get(0).getSalesDate());
        additionModel.setAdditionStartDateLong(additionService.dateConvertLong(0,sales.get(0).getSalesDate()));
        TablesModel table=tablesRepo.findByTableName(additionModel.getTableName());
        table.setPayment(additionModel.getPayment());
        tablesRepo.save(table);
        additionRepo.save(additionModel);
    }

    public List<SalesModel> getSalesByCompleteOrderAndOrderStatusAndCancelSales(int completeOrder, int orderStatus, int cancelSales) {
        if (orderStatus==0){
            Date date = new Date();
            long a=date.getTime();
            long b=a-43200*1000;
            return salesRepo.findAllBySalesStartDateLongGreaterThanEqualAndSalesStartDateLongLessThanEqual(b,a);
        }
        List<SalesModel> salesModels=salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSales(completeOrder,orderStatus,cancelSales);
        List<SalesModel> sales=new ArrayList<>();
        for (SalesModel salesModel:salesModels) {
            if(categoriesRepo.findByCategoryName(salesModel.getCategoryName()).getCategoryType()==1){
                sales.add(salesModel);
            }
        }
        return sales;
    }

    public List<SalesModel> getActiveSalesByTableName(String tableName) {
        return salesRepo.findAllByOrderStatusAndTableName(1,tableName);
    }

    public List<SalesModel> getSalesByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(int completeOrder, int orderStatus, int cancelSales,String tableName) {
        return salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(completeOrder,orderStatus,cancelSales,tableName);
    }

    public String setSalesStartDateById(long id) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        SalesModel salesModel = salesRepo.findById(id);
        salesModel.setSalesStartDate(nowDate);
        salesModel.setSalesStartDateLong(date.getTime());
        salesModel.setCompleteOrder(1);
        salesRepo.save(salesModel);
        return "200";
    }

    public String setSalesFinishDateById(long id) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        SalesModel salesModel = salesRepo.findById(id);
        salesModel.setSalesFinishDate(nowDate);
        salesModel.setSalesFinishDateLong(date.getTime());
        salesModel.setCompleteOrder(0);
        salesModel.setOrderStatus(0);
        salesRepo.save(salesModel);
        return "200";
    }

    public List<SalesModel> getCancelSales() {
        Date date = new Date();
        long nowDate = date.getTime();
        long dateBeforeOneDay = nowDate-86400*1000;
        return salesRepo.findAllByCancelSalesDateLongGreaterThanEqualAndCancelSalesDateLongLessThanEqualAndCancelSalesCheck(dateBeforeOneDay, nowDate,0);
    }

    public SalesModel cancelSale(CancelSaleModel cancelSaleModel) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        SalesModel salesModel = salesRepo.findById(cancelSaleModel.getSaleId());
        salesModel.setCancelSales(1);
        salesModel.setOrderStatus(0);
        salesModel.setCompleteOrder(0);
        salesModel.setCancelSalesDate(nowDate);
        salesModel.setCancelSalesDateLong(date.getTime());
        salesModel.setComment(cancelSaleModel.getComment());
        salesModel.setCancelUserNo(cancelSaleModel.getUserNo());
        salesRepo.save(salesModel);
        AdditionModel additionModel=additionRepo.findById(salesModel.getAdditionNo());
        double newAdditionPrice=additionModel.getPayment()-salesModel.getTotalPrice();
        if (newAdditionPrice<=0){
            additionModel.setActivity(0);
            additionModel.setPayment(0);
        }
        else {
            additionModel.setPayment(newAdditionPrice);
        }
        additionRepo.save(additionModel);
        TablesModel table=tablesRepo.findByTableName(salesModel.getTableName());
        table.setPayment(calculateTotalPay(salesModel));
        tablesRepo.save(table);
        return salesModel;
    }

    public String cancelSaleCheck(long salesId) {
        SalesModel salesModel = salesRepo.findById(salesId);
        salesModel.setCancelSalesCheck(1);
        salesRepo.save(salesModel);
        return "200";
    }

    public double calculateTotalPay(SalesModel salesModel) {
        double a=0;
        List<SalesModel> sales=salesRepo.findAllByAdditionNoAndCancelSales(salesModel.getAdditionNo(),0);
        for (SalesModel sale: sales) {
            a+=sale.getTotalPrice();
        }
        return a;
    }
}
