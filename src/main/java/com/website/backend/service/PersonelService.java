package com.website.backend.service;

import com.website.backend.model.*;
import com.website.backend.repository.CashOutflowRepo;
import com.website.backend.repository.PersonelRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonelService {

    private final PersonelRepo personelRepo;
    private final AdditionService additionService;
    private final CashOutflowRepo cashOutflowRepo;

    public PersonelService(PersonelRepo personelRepo,AdditionService additionService,CashOutflowRepo cashOutflowRepo) {
        this.personelRepo = personelRepo;
        this.additionService = additionService;
        this.cashOutflowRepo = cashOutflowRepo;
    }

    public PersonelModel save(PersonelModel personelModel) {
        if (personelRepo.findByName(personelModel.getName()) != null) {
            return null;
        }
        personelRepo.save(personelModel);
        return personelModel;
    }

    public String delete(long id) {
        personelRepo.delete(personelRepo.findById(id));
        return "200";
    }

    public List<PersonelModel> getAll() {
        return personelRepo.findAll();
    }

    public List<PersonalPayModel> getPersonalPayInfo(String startDate,String finishDate) {
        List<CashOutflowModel> cashOutflowModels;
        List<PersonalPayModel> personalPayModelList = new ArrayList<>();
        try {
            List<Long> arr = additionService.convertLong(startDate, finishDate);
            long a=arr.get(0).longValue();
            long b=arr.get(1).longValue();
            List<PersonelModel> personelModels=getAll();
            for (PersonelModel personelModel:personelModels) {
                PersonalPayModel personalPayModel=new PersonalPayModel();
                cashOutflowModels = cashOutflowRepo.findAllByDateLongGreaterThanEqualAndDateLongLessThanEqualAndPersonelName(a, b,personelModel.getName());
                double totalExpense = additionService.sumExpense(cashOutflowModels);
                personalPayModel.setRemainingMoney(personelModel.getSalary()-totalExpense);
                personalPayModel.setPersonalId(personelModel.getId());
                personalPayModel.setPersonalSalary(personelModel.getSalary());
                personalPayModel.setPersonalName(personelModel.getName());

                personalPayModelList.add(personalPayModel);
            }
            return personalPayModelList;
        } catch (Exception e) {
            return null;
        }
    }
}
