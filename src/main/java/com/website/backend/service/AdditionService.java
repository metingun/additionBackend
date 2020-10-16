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

    public List<SalesModel> getAdditionByTableNameAndActivity(TablesModel tablesModel) {
        long additionId = additionRepo.findByTableNameAndActivity(tablesModel.getTableName(), 1).getId();
        return salesRepo.findAllByAdditionNoAndCancelSales(additionId, 0);
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
            long a=arr.get(0).longValue();
            long b=arr.get(1).longValue();

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
        double dailyIncome = 0;
        double cashTotal = 0;
        double creditTotal = 0;

        DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();

        for (AdditionModel additionalModel : additionModels) {
            dailyIncome += additionalModel.getDiscountedPayment();
            cashTotal += additionalModel.getCashPayment();
            creditTotal += additionalModel.getCreditCardPayment();
        }
        dailyIncomeModel.setCashIncome(cashTotal);
        dailyIncomeModel.setCreditCardIncome(creditTotal);
        dailyIncomeModel.setDailyIncome(dailyIncome);

        return dailyIncomeModel;
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
        return "200";
    }

    public double calculateDiscountedPrice(AdditionModel additionModel, AdditionModel addition) {
        double discountRate = discountTypeRepo.findByDiscountName(additionModel.getDiscountName()).getDiscountRate();
        return (1 - discountRate / 100) * addition.getPayment();
    }
}
