package com.website.backend.service;

import com.website.backend.model.*;
import com.website.backend.repository.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesService {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private final SalesRepo salesRepo;
    private final ProductRepo productRepo;
    private final TablesRepo tablesRepo;
    private final AdditionRepo additionRepo;
    private final AdditionService additionService;
    private final CategoriesRepo categoriesRepo;
    private final CompanyRepo companyRepo;

    public SalesService(CompanyRepo companyRepo, SalesRepo salesRepo, ProductRepo productRepo, TablesRepo tablesRepo, AdditionRepo additionRepo, AdditionService additionService, CategoriesRepo categoriesRepo) {
        this.salesRepo = salesRepo;
        this.productRepo = productRepo;
        this.tablesRepo = tablesRepo;
        this.additionRepo = additionRepo;
        this.additionService = additionService;
        this.categoriesRepo = categoriesRepo;
        this.companyRepo = companyRepo;
    }

    public List<SalesModel> save(List<SalesModel> sales) throws ParseException {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        int menuType = tablesRepo.findByTableName(sales.get(0).getTableName()).getMenuType();
        for (SalesModel sale : sales) {
            ProductModel productModel = productRepo.findByProductNo(sale.getProductNo());
            if (menuType == 0) {
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
    public String saveAll(List<SalesModel> salesModels){
        salesRepo.saveAll(salesModels);
        return "Successful";
    }

    public List<SalesModel> getAll(){
        return salesRepo.findAll();
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
        additionModel.setAdditionStartDateLong(additionService.dateConvertLong(0, sales.get(0).getSalesDate()));
        TablesModel table = tablesRepo.findByTableName(additionModel.getTableName());
        table.setPayment(additionModel.getPayment());
        tablesRepo.save(table);
        additionRepo.save(additionModel);
    }

    public List<SalesModel> getSalesByCompleteOrderAndOrderStatusAndCancelSales(int completeOrder, int orderStatus, int cancelSales, int categoryType) {
        List<SalesModel> salesModels = new ArrayList<>();
        if (categoryType == 0) {
            return salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSales(1, 1, 0);
        } else if (completeOrder == orderStatus && cancelSales == 0) {
            Date date = new Date();
            long a = date.getTime();
            long b = a - 43200 * 1000;
            salesModels.addAll(salesRepo.findAllBySalesStartDateLongGreaterThanEqualAndSalesStartDateLongLessThanEqualAndCompleteOrderAndOrderStatusAndCancelSales(b, a, 1, 1, 0));
            salesModels.addAll(salesRepo.findAllBySalesStartDateLongGreaterThanEqualAndSalesStartDateLongLessThanEqualAndCompleteOrderAndOrderStatusAndCancelSales(b, a, 0, 0, 0));
        } else {
            salesModels = salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSales(completeOrder, orderStatus, cancelSales);
        }

        return getSalesListByCategoryType(salesModels, categoryType);
    }

    private List<SalesModel> getSalesListByCategoryType(List<SalesModel> salesModels, int categoryType) {
        List<SalesModel> sales = new ArrayList<>();
        for (SalesModel salesModel : salesModels) {
            if (categoriesRepo.findByCategoryName(salesModel.getCategoryName()).getCategoryType() == categoryType) {
                sales.add(salesModel);
            }
        }
        return sales;
    }

    public List<SalesModel> getActiveSalesByTableName(String tableName) {
        return salesRepo.findAllByOrderStatusAndTableName(1, tableName);
    }

    public List<SalesModel> getSalesByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(int completeOrder, int orderStatus, int cancelSales, String tableName) {
        return salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(completeOrder, orderStatus, cancelSales, tableName);
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
        long dateBeforeOneDay = nowDate - 86400 * 1000;
        return salesRepo.findAllByCancelSalesDateLongGreaterThanEqualAndCancelSalesDateLongLessThanEqualAndCancelSalesCheck(dateBeforeOneDay, nowDate, 0);
    }

    public SalesModel cancelSale(CancelSaleModel cancelSaleModel) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        SalesModel salesModel = salesRepo.findById(cancelSaleModel.getSaleId());
        if (cancelSaleModel.getQuantity() >= salesModel.getQuantity()) {
            return cancelAllSale(salesModel, nowDate, date, cancelSaleModel);
        } else {
            return cancelQuantitySale(salesModel, nowDate, date, cancelSaleModel);
        }
    }

    private SalesModel cancelAllSale(SalesModel salesModel, String nowDate, Date date, CancelSaleModel cancelSaleModel) {
        salesRepo.save(setCancelSaleFields(salesModel, nowDate, date, cancelSaleModel));
        updateAdditionAndTableForCancelSale(salesModel);
        return salesModel;
    }

    public SalesModel cancelQuantitySale(SalesModel salesModel, String nowDate, Date date, CancelSaleModel cancelSaleModel) {

        SalesModel salesModel1 = setSalesFields(salesModel);

        salesModel1.setQuantity(cancelSaleModel.getQuantity());
        salesModel1.setCancelSalesCheck(1);
        salesModel1.setTotalPrice(salesModel1.getUnitPrice() * salesModel1.getQuantity());

        salesModel.setQuantity(salesModel.getQuantity() - cancelSaleModel.getQuantity());
        salesModel.setTotalPrice(salesModel.getUnitPrice() * salesModel.getQuantity());

        salesRepo.save(salesModel);
        salesRepo.save(setCancelSaleFields(salesModel1, nowDate, date, cancelSaleModel));
        updateAdditionAndTableForCancelSale(salesModel1);

        return setCancelSaleFields(salesModel1, nowDate, date, cancelSaleModel);
    }

    private SalesModel setCancelSaleFields(SalesModel salesModel, String nowDate, Date date, CancelSaleModel cancelSaleModel) {
        salesModel.setCancelSales(1);
        salesModel.setOrderStatus(0);
        salesModel.setCompleteOrder(0);
        salesModel.setCancelSalesDate(nowDate);
        salesModel.setCancelSalesDateLong(date.getTime());
        salesModel.setComment(cancelSaleModel.getComment());
        salesModel.setCancelUserNo(cancelSaleModel.getUserNo());
        return salesModel;
    }

    private SalesModel setSalesFields(SalesModel salesModel) {
        SalesModel salesModel1 = new SalesModel();
        salesModel1.setSalesStartDate(salesModel.getSalesStartDate());
        salesModel1.setSalesStartDateLong(salesModel.getSalesStartDateLong());
        salesModel1.setTableName(salesModel.getTableName());
        salesModel1.setAdditionNo(salesModel.getAdditionNo());
        salesModel1.setSalesFinishDateLong(salesModel.getSalesFinishDateLong());
        salesModel1.setSalesFinishDate(salesModel.getSalesFinishDate());
        salesModel1.setSalesDate(salesModel.getSalesDate());
        salesModel1.setMenuType(salesModel.getMenuType());
        salesModel1.setUnitPrice(salesModel.getUnitPrice());
        salesModel1.setSalesDateLong(salesModel.getSalesDateLong());
        salesModel1.setCategoryName(salesModel.getCategoryName());
        salesModel1.setProductNo(salesModel.getProductNo());
        salesModel1.setProductName(salesModel.getProductName());
        return salesModel1;
    }

    private void updateAdditionAndTableForCancelSale(SalesModel salesModel) {
        AdditionModel additionModel = additionRepo.findById(salesModel.getAdditionNo());
        double newAdditionPrice = additionModel.getPayment() - salesModel.getTotalPrice();
        if (newAdditionPrice <= 0) {
            additionModel.setActivity(0);
            additionModel.setPayment(0);
        } else {
            additionModel.setPayment(newAdditionPrice);
        }
        additionRepo.save(additionModel);
        TablesModel table = tablesRepo.findByTableName(salesModel.getTableName());
        table.setPayment(calculateTotalPay(salesModel.getAdditionNo()));
        tablesRepo.save(table);
    }

    public String cancelSaleCheck(long salesId) {
        SalesModel salesModel = salesRepo.findById(salesId);
        salesModel.setCancelSalesCheck(1);
        salesRepo.save(salesModel);
        return "200";
    }

    private double calculateTotalPay(long additionNo) {
        double a = 0;
        List<SalesModel> sales = salesRepo.findAllByAdditionNoAndCancelSales(additionNo, 0);
        for (SalesModel sale : sales) {
            a += sale.getTotalPrice();
        }
        return a;
    }

    public void salesTransferToEmptyTable(TableTransferModel tableTransferModel) {
        long additionId = additionRepo.findByTableNameAndActivity(tableTransferModel.getFromTable(), 1).getId();
        List<SalesModel> salesModels = salesRepo.findAllByAdditionNo(additionId);
        for (SalesModel sale : salesModels) {
            sale.setTableName(tableTransferModel.getToTable());
            salesRepo.save(sale);
        }
    }

    public void salesTransfer(TableTransferModel tableTransferModel) {
        long additionId = additionRepo.findByTableNameAndActivity(tableTransferModel.getFromTable(), 1).getId();
        long additionId2 = additionRepo.findByTableNameAndActivity(tableTransferModel.getToTable(), 1).getId();
        List<SalesModel> salesModels = salesRepo.findAllByAdditionNo(additionId);
        for (SalesModel sale : salesModels) {
            sale.setTableName(tableTransferModel.getToTable());
            sale.setAdditionNo(additionId2);
            salesRepo.save(sale);
        }
    }

/*    public void oneSaleTransfer(TableTransferModel tableTransferModel) {
        long additionId=additionRepo.findByTableNameAndActivity(tableTransferModel.getFromTable(),1).getId();
        List<SalesModel> salesModels= salesRepo.findAllByAdditionNo(additionId);
        for (SalesModel sale:salesModels) {
            sale.setTableName(tableTransferModel.getToTable());
            salesRepo.save(sale);
        }
    }

    public void oneSaleTransferToEmptyTable(TableTransferModel tableTransferModel) {
        SalesModel salesModel=salesRepo.findById(tableTransferModel.getSaleId());
        AdditionModel additionModel=new AdditionModel();
        additionModel.setTableName(tableTransferModel.getToTable());
        if (tableTransferModel.getQuantity()>=salesModel.getQuantity()){

        }

    }*/

    public SaleByRayonModel getSaleByRayon(String startDate, String finishDate) {
        List<AdditionModel> additionModels;
        SaleByRayonModel saleByRayonModel = new SaleByRayonModel();
        double kitchen = 0;
        double bar = 0;
        double nargile = 0;
        int kitchenQty = 0;
        int barQty = 0;
        int nargileQty = 0;
        try {
            List<Long> arr = convertLong(startDate, finishDate);
            additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(arr.get(0), arr.get(1), 0);
            for (AdditionModel addition : additionModels) {
                List<SalesModel> salesModels = salesRepo.findAllByAdditionNoAndCancelSales(addition.getId(), 0);
                for (SalesModel salesModel : salesModels) {
                    CategoriesModel categoriesModel = categoriesRepo.findByCategoryName(salesModel.getCategoryName());
                    int categoryType = categoriesModel.getCategoryType();
                    if (categoryType == 1) {
                        kitchen += salesModel.getTotalPrice();
                        kitchenQty += salesModel.getQuantity();
                    } else if (categoryType == 2) {
                        bar += salesModel.getTotalPrice();
                        barQty += salesModel.getQuantity();
                    } else {
                        nargile += salesModel.getTotalPrice();
                        nargileQty += salesModel.getQuantity();
                    }
                }
            }

            saleByRayonModel.setBar(bar);
            saleByRayonModel.setBarQty(barQty);
            saleByRayonModel.setKitchen(kitchen);
            saleByRayonModel.setKitchenQty(kitchenQty);
            saleByRayonModel.setNargile(nargile);
            saleByRayonModel.setNargileQty(nargileQty);
            return saleByRayonModel;
        } catch (Exception e) {
            return null;
        }

    }

    public List<SalesModel> getSaleListByRayon(String startDate, String finishDate, int categoryType) {
        List<AdditionModel> additionModels;
        List<SalesModel> salesByRayon = new ArrayList<>();
        try {
            List<Long> arr = convertLong(startDate, finishDate);
            additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(arr.get(0), arr.get(1), 0);
            for (AdditionModel addition : additionModels) {
                List<SalesModel> salesModels = salesRepo.findAllByAdditionNoAndCancelSales(addition.getId(), 0);
                for (SalesModel salesModel : salesModels) {
                    salesModel.setSalesDate(addition.getAdditionFinishDate());
                    CategoriesModel categoriesModel = categoriesRepo.findByCategoryName(salesModel.getCategoryName());
                    if (categoryType == categoriesModel.getCategoryType()) {
                        salesByRayon.add(salesModel);
                    }
                }
            }

            return salesByRayon;
        } catch (Exception e) {
            return null;
        }

    }

    public List<FavouriteProductModel> favouriteProductsList(int sortType) {
        List<ProductModel> productModels = productRepo.findAll();
        List<SalesModel> salesModels;
        List<FavouriteProductModel> favouriteProductModels = new ArrayList<>();
        for (ProductModel productModel : productModels) {
            FavouriteProductModel favouriteProductModel = new FavouriteProductModel();
            int i = 0;
            if (sortType == 3) {
                salesModels = salesRepo.findAllByProductNoAndCancelSales(productModel.getProductNo(), 1);
            } else {
                salesModels = salesRepo.findAllByProductNoAndCancelSales(productModel.getProductNo(), 0);
            }
            for (SalesModel salesModel : salesModels) {
                i += salesModel.getQuantity();
            }
            favouriteProductModel.setProductNo(productModel.getProductNo());
            favouriteProductModel.setProductName(productModel.getProductName());
            favouriteProductModel.setTotalQuantity(i);
            favouriteProductModels.add(favouriteProductModel);
        }
        if (sortType == 1) {
            favouriteProductModels = favouriteProductModels.stream()
                    .sorted(Comparator.comparing(FavouriteProductModel::getTotalQuantity))
                    .collect(Collectors.toList());
        } else {
            favouriteProductModels = favouriteProductModels.stream()
                    .sorted(Comparator.comparing(FavouriteProductModel::getTotalQuantity).reversed())
                    .collect(Collectors.toList());
        }


        return favouriteProductModels;
    }

    private List<Long> convertLong(String startDate, String finishDate) throws ParseException {
        List<Long> arr = new ArrayList<>();
        long finish;
        long start = dateConvertLong(0, startDate);
        if (finishDate.equals(" 08:00")) {
            finish = dateConvertLong(1, startDate);
        } else {
            finish = dateConvertLong(0, finishDate);
        }
        arr.add(start);
        arr.add(finish);
        return arr;
    }

    private long dateConvertLong(int adding, String startDate) throws ParseException {
        Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(startDate);
        long a = date.getTime();
        if (adding == 1) {
            a += 86400 * 1000;
        }
        return a;
    }

    public List<SaleByCompanyModel> getSalesTotalByCompany(String startDate, String finishDate) throws ParseException {
        try {
            List<CompanyModel> companyModels = companyRepo.findAll();
            List<SaleByCompanyModel> companySales = new ArrayList<>();

            List<Long> arr = additionService.convertLong(startDate, finishDate);
            long a = arr.get(0).longValue();
            long b = arr.get(1).longValue();

            for (CompanyModel companyModel : companyModels) {
                SaleByCompanyModel saleByCompanyModel=new SaleByCompanyModel();
                double totalSales = 0;
                if (companyModel.getMenuType() == 1) {
                    List<AdditionModel> companyOutAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameContains(a, b, 0, companyModel.getCompanyName());
                    for (AdditionModel additionModel : companyOutAdditions) {
                        totalSales += additionModel.getCashPayment() + additionModel.getCreditCardPayment();
                    }
                } else if (companyModel.getMenuType() == 0) {
                    List<AdditionModel> companyInAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameNotContains(a, b, 0, "||");
                    for (AdditionModel additionModel : companyInAdditions) {
                        if (tablesRepo.findByTableName(additionModel.getTableName()).getTableType().equals(companyModel.getCompanyName())) {
                            totalSales += additionModel.getCashPayment() + additionModel.getCreditCardPayment();
                        }
                    }
                }
                saleByCompanyModel.setCompanyId(companyModel.getId());
                saleByCompanyModel.setCompanyName(companyModel.getCompanyName());
                saleByCompanyModel.setTotalSales(totalSales);
                companySales.add(saleByCompanyModel);
            }
            SaleByCompanyModel saleByCompanyModel1=new SaleByCompanyModel();

            double totalSalesOutSale = 0;
            List<AdditionModel> outAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameContains(a, b, 0, "||");
            for (AdditionModel additionModel : outAdditions) {
                String c = additionModel.getTableName().split("\\|\\|")[0];
                if (companyRepo.findByCompanyName(c) == null) {
                    totalSalesOutSale += additionModel.getCashPayment() + additionModel.getCreditCardPayment();
                }
            }

            saleByCompanyModel1.setCompanyId(-1);
            saleByCompanyModel1.setCompanyName("Dış Siparişler");
            saleByCompanyModel1.setTotalSales(totalSalesOutSale);
            companySales.add(saleByCompanyModel1);
            return companySales;

        } catch (Exception e) {
            return null;
        }
    }

    public List<AdditionModel> getSalesDetailByCompany(String startDate, String finishDate, long companyId) throws ParseException {
        try {
            CompanyModel companyModel = companyRepo.findById(companyId);
            List<AdditionModel> companyOutAdditions=new ArrayList<>();
            List<Long> arr = additionService.convertLong(startDate, finishDate);
            long a = arr.get(0).longValue();
            long b = arr.get(1).longValue();

            if (companyModel==null){
                List<AdditionModel> outAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameContains(a, b, 0, "||");
                for (AdditionModel additionModel : outAdditions) {
                    String c = additionModel.getTableName().split("\\|\\|")[0];
                    if (companyRepo.findByCompanyName(c) == null) {
                        companyOutAdditions.add(additionModel);
                    }
                }
            }
            else if (companyModel.getMenuType() == 1) {
                companyOutAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameContains(a, b, 0, companyModel.getCompanyName());
            }
            else if (companyModel.getMenuType() == 0) {
                List<AdditionModel> companyInAdditions = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameNotContains(a, b, 0, "||");
                for (AdditionModel additionModel : companyInAdditions) {
                    if (tablesRepo.findByTableName(additionModel.getTableName()).getTableType().equals(companyModel.getCompanyName())) {
                        companyOutAdditions.add(additionModel);
                    }
                }
            }


            return companyOutAdditions;

        } catch (Exception e) {
            return null;
        }
    }
}
