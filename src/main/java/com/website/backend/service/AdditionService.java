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
public class AdditionService {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final AdditionRepo additionRepo;
    private final SalesRepo salesRepo;
    private final DiscountTypeRepo discountTypeRepo;
    private final TablesRepo tablesRepo;
    private final CashOutflowRepo cashOutflowRepo;

    public AdditionService(AdditionRepo additionRepo, SalesRepo salesRepo, DiscountTypeRepo discountTypeRepo, TablesRepo tablesRepo, CashOutflowRepo cashOutflowRepo) {
        this.additionRepo = additionRepo;
        this.salesRepo = salesRepo;
        this.discountTypeRepo = discountTypeRepo;
        this.tablesRepo = tablesRepo;
        this.cashOutflowRepo = cashOutflowRepo;
    }

    public List<AdditionModel> getAll() {
        return additionRepo.findAll();
    }

    public String saveAll(List<AdditionModel> additionModels) {
        additionRepo.saveAll(additionModels);
        return "Successful";
    }

    public AdditionModel getAdditionById(long id) {
        return additionRepo.findById(id);
    }

    public String update(AdditionModel additionModel) {
        AdditionModel additionModel1=additionRepo.findById(additionModel.getId());
        additionModel1.setCashPayment(additionModel.getCashPayment());
        additionModel1.setCreditCardPayment(additionModel.getCreditCardPayment());
        additionModel1.setActivity(additionModel.getActivity());
        additionRepo.save(additionModel1);
        return "Successfully Updated.";
    }

    public List<SalesModel> getAdditionByTableNameAndActivity(TablesModel tablesModel) {
        long additionId = additionRepo.findByTableNameAndActivity(tablesModel.getTableName(), 1).getId();
        return salesRepo.findAllByAdditionNoAndCancelSales(additionId, 0);
    }

    public List<SalesModel> getAdditionDetailById(long id) {
        return salesRepo.findAllByAdditionNoAndCancelSales(id, 0);
    }

    public List<Long> convertLong(String startDate, String finishDate) throws ParseException {
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

    public DailyIncomeModel getAdditionTotalByDate(String startDate, String finishDate) {
        List<CashOutflowModel> cashOutflowModels;
        List<AdditionModel> additionModels;
        try {
            List<Long> arr = convertLong(startDate, finishDate);
            additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(arr.get(0), arr.get(1), 0);
            long a= arr.get(0);
            long b= arr.get(1);

            cashOutflowModels = cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqual(a, b);
            DailyIncomeModel dailyIncomeModel = sumPaymentAdditionModels(additionModels);
            double totalExpense = sumExpense(cashOutflowModels);
            dailyIncomeModel.setTotalExpense(totalExpense);
            dailyIncomeModel.setSafeTotal(dailyIncomeModel.getCashIncome() - totalExpense);
            dailyIncomeModel.setTotalProfit(dailyIncomeModel.getDailyIncome() - totalExpense);
            return dailyIncomeModel;
        } catch (Exception e) {
            return null;
        }

    }

    public List<AdditionModel> getAdditionsByDate(String startDate, String finishDate, int paymentType) {
        List<AdditionModel> additionModels;
        try {
            List<Long> arr = convertLong(startDate, finishDate);
            switch (paymentType) {
                case 0:
                    additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(arr.get(0), arr.get(1), 0);
                    break;

                case 1:
                    additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndCashPaymentGreaterThan(arr.get(0), arr.get(1), 0, 0);
                    break;

                case 2:
                    additionModels = additionRepo.findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndCreditCardPaymentGreaterThan(arr.get(0), arr.get(1), 0, 0);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + paymentType);
            }
            return additionModels;
        } catch (Exception e) {
            return null;
        }
    }

    public long dateConvertLong(int adding, String startDate) throws ParseException {
        Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(startDate);
        long a = date.getTime();
        if (adding == 1) {
            a += 86400 * 1000;
        }
        return a;
    }

    public double sumExpense(List<CashOutflowModel> cashOutflows) {
        double totalExpense = 0;

        for (CashOutflowModel cashOutflow : cashOutflows) {
            totalExpense += cashOutflow.getPrice();

        }
        return totalExpense;
    }

    private DailyIncomeModel sumPaymentAdditionModels(List<AdditionModel> additionModels) {
        double cashTotal = 0;
        double creditTotal = 0;

        DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();

        for (AdditionModel additionalModel : additionModels) {
            cashTotal += additionalModel.getCashPayment();
            creditTotal += additionalModel.getCreditCardPayment();
        }
        dailyIncomeModel.setCashIncome(cashTotal);
        dailyIncomeModel.setCreditCardIncome(creditTotal);
        dailyIncomeModel.setDailyIncome(cashTotal+creditTotal);

        return dailyIncomeModel;
    }

    public String payPartialBill(AdditionModel additionModel) {
        Date date = new Date();
        String formatDate = dateFormat.format(date);
        long nowDate = date.getTime();
        AdditionModel addition = additionRepo.findByTableNameAndActivity(additionModel.getTableName(), 1);
        additionModel.setPayment(additionModel.getCashPayment()+additionModel.getCreditCardPayment());
        additionModel.setDiscountedPayment(additionModel.getCashPayment()+additionModel.getCreditCardPayment());
        additionModel.setAdditionFinishDateLong(nowDate);
        additionModel.setAdditionFinishDate(formatDate);
        additionModel.setAdditionStartDate(addition.getAdditionStartDate());
        additionModel.setAdditionStartDateLong(addition.getAdditionStartDateLong());
        if (additionModel.getDiscountName().equals("İndirim seçiniz")) {
            additionModel.setDiscountedPayment(additionModel.getPayment());
            additionModel.setDiscountName("yok");
        } else {
            additionModel.setDiscountedPayment(calculateDiscountedPrice(additionModel, addition));
        }
        additionModel.setActivity(0);
        TablesModel table = tablesRepo.findByTableName(addition.getTableName());
        if (table.getMenuType() == 1&&table.getPayment()==(additionModel.getCashPayment()+additionModel.getCreditCardPayment())) {
            tablesRepo.delete(table);
        } else {
            table.setPayment(table.getPayment()-additionModel.getDiscountedPayment());
            tablesRepo.save(table);
        }
        addition.setPayment(addition.getPayment()-additionModel.getDiscountedPayment());
        addition.setDiscountedPayment(addition.getDiscountedPayment()-additionModel.getDiscountedPayment());
        additionRepo.save(addition);
        additionRepo.save(additionModel);
        return "200";
    }

    public String payBill(AdditionModel additionModel) {
        Date date = new Date();
        String formatDate = dateFormat.format(date);
        long nowDate = date.getTime();
        AdditionModel addition = additionRepo.findByTableNameAndActivity(additionModel.getTableName(), 1);
        addition.setAdditionFinishDateLong(nowDate);
        addition.setAdditionFinishDate(formatDate);
        addition.setCashPayment(additionModel.getCashPayment());
        addition.setCreditCardPayment(additionModel.getCreditCardPayment());
        if (additionModel.getDiscountName().equals("İndirim seçiniz")) {
            addition.setDiscountedPayment(addition.getPayment());
            addition.setDiscountName("yok");
        } else {
            addition.setDiscountName(additionModel.getDiscountName());
            addition.setDiscountedPayment(calculateDiscountedPrice(additionModel, addition));
        }
        addition.setActivity(0);
        TablesModel table = tablesRepo.findByTableName(addition.getTableName());
        if (table.getMenuType() == 1) {
            tablesRepo.delete(table);
        } else {
            table.setPayment(0);
            tablesRepo.save(table);
        }
        additionRepo.save(addition);

        List<SalesModel> salesModels= salesRepo.findAllByAdditionNo(addition.getId());
        for (SalesModel sales:salesModels) {
            sales.setCompleteOrder(0);
            sales.setOrderStatus(0);
            salesRepo.save(sales);
        }
        return "200";
    }

    public double calculateDiscountedPrice(AdditionModel additionModel, AdditionModel addition) {
        double discountRate = discountTypeRepo.findByDiscountName(additionModel.getDiscountName()).getDiscountRate();
        return (1 - discountRate) * addition.getPayment();
    }

    public void additionTransferToEmptyTable(TableTransferModel tableTransferModel) {
        AdditionModel additionModel=additionRepo.findByTableNameAndActivity(tableTransferModel.getFromTable(),1);
        additionModel.setTableName(tableTransferModel.getToTable());
        additionRepo.save(additionModel);
    }

    public void additionTransfer(TableTransferModel tableTransferModel) {
        AdditionModel additionModel=additionRepo.findByTableNameAndActivity(tableTransferModel.getToTable(),1);
        AdditionModel additionModel2=additionRepo.findByTableNameAndActivity(tableTransferModel.getFromTable(),1);
        additionModel.setPayment(calculateTotalPay(additionModel.getId()));
        additionRepo.delete(additionModel2);
        additionRepo.save(additionModel);
    }

    public AdditionModel getOneAdditionByTableNameAndActivity(TablesModel tablesModel) {
        return additionRepo.findByTableNameAndActivity(tablesModel.getTableName(),1);
    }

    private double calculateTotalPay(long additionNo) {
        double a = 0;
        List<SalesModel> sales = salesRepo.findAllByAdditionNoAndCancelSales(additionNo, 0);
        for (SalesModel sale : sales) {
            a += sale.getTotalPrice();
        }
        return a;
    }
}
