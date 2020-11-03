package com.website.backend.service;

import com.website.backend.model.*;
import com.website.backend.repository.CashOutflowRepo;
import com.website.backend.repository.OutcomeTypeRepo;
import com.website.backend.repository.PersonelRepo;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CashOutflowService {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final CashOutflowRepo cashOutflowRepo;
    private final AdditionService additionService;
    private final OutcomeTypeRepo outcomeTypeRepo;
    private final PersonelRepo personelRepo;

    public CashOutflowService(CashOutflowRepo cashOutflowRepo,AdditionService additionService,OutcomeTypeRepo outcomeTypeRepo,PersonelRepo personelRepo) {
        this.cashOutflowRepo = cashOutflowRepo;
        this.additionService = additionService;
        this.outcomeTypeRepo = outcomeTypeRepo;
        this.personelRepo = personelRepo;
    }

    public CashOutflowModel save(CashOutflowModel cashOutflowModel) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        long a=date.getTime();
        cashOutflowModel.setDate(nowDate);
        cashOutflowModel.setDateLong(a);
        if (cashOutflowModel.getTypeId()!=0){
            cashOutflowModel.setType(outcomeTypeRepo.findById(cashOutflowModel.getTypeId()).getOutcomeType());
        }
        else {
            cashOutflowModel.setType("Personel Ödemesi");
        }
        cashOutflowRepo.save(cashOutflowModel);
        return cashOutflowModel;
    }

/*    public String delete(long id) {
        cashOutflowRepo.delete(cashOutflowRepo.findById(id));
        return "200";
    }*/

    public List<CashOutflowModel> getAll() {
        return cashOutflowRepo.findAll();
    }

    public CashOutflowModel getCashOutflowById(long id) {
        return cashOutflowRepo.findById(id);
    }

    public String update(CashOutflowModel cashOutflowModel) {
        CashOutflowModel cashOutflowModel1=cashOutflowRepo.findById(cashOutflowModel.getId());
        cashOutflowModel1.setPrice(cashOutflowModel.getPrice());
        cashOutflowRepo.save(cashOutflowModel1);
        return "Successfully";
    }

    public String saveAll(List<CashOutflowModel> cashOutflowModels) {
        cashOutflowRepo.saveAll(cashOutflowModels);
        return "Successful";
    }

    public List<CashOutflowModel> getAllByDate(String startDate,String finishDate) throws ParseException {
        List<Long> dateLong=additionService.convertLong(startDate,finishDate);
        return cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqual(dateLong.get(0),dateLong.get(1));
    }

    public List<CashOutflowModel> getTotalPersonalByDate(String startDate,String finishDate) throws ParseException {
        List<Long> dateLong=additionService.convertLong(startDate,finishDate);
        return cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqualAndTypeId(dateLong.get(0),dateLong.get(1),0);
    }

    public List<CashOutflowModel> getCashflowByDateAndPersonelId(String startDate,String finishDate,long personelId) throws ParseException {
        List<Long> dateLong=additionService.convertLong(startDate,finishDate);
        return cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqualAndPersonelName(dateLong.get(0),dateLong.get(1),personelRepo.findById(personelId).getName());
    }

    public DailyOutcomeModel getTotalsByOutcomeType(String startDate, String finishDate) throws ParseException {
        List<CashOutflowModel> cashOutflowModels;
        DailyOutcomeModel dailyOutcomeModel=new DailyOutcomeModel();
        try {
            List<Long> arr = additionService.convertLong(startDate, finishDate);

            long a=arr.get(0).longValue();
            long b=arr.get(1).longValue();

            cashOutflowModels = cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqual(a, b);
            List<CashOutflowModel> personalOutcomes = cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqualAndTypeId(a, b,0);
            double totalExpense = additionService.sumExpense(cashOutflowModels);
            double totalPersonalExpense = additionService.sumExpense(personalOutcomes);
            dailyOutcomeModel.setTotalOutcome(totalExpense);
            dailyOutcomeModel.setTotalPersonalOutcome(totalPersonalExpense);

            List<OutcomeTypeModel> outcomeTypeModels = outcomeTypeRepo.findAll();
            HashMap<String,Double> outcomeType=new HashMap<>();
            for (OutcomeTypeModel outcome:outcomeTypeModels) {
                List<CashOutflowModel> outcomesByType = cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqualAndTypeId(a, b,outcome.getId());
                outcomeType.put(outcome.getOutcomeType(),additionService.sumExpense(outcomesByType));
            }
            dailyOutcomeModel.setOutcomeTypes(outcomeType);
            return dailyOutcomeModel;

        } catch (Exception e) {
            return null;
        }
    }
}
