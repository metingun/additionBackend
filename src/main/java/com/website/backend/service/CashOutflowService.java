package com.website.backend.service;

import com.website.backend.model.CashOutflowModel;
import com.website.backend.model.PersonelModel;
import com.website.backend.repository.CashOutflowRepo;
import com.website.backend.repository.PersonelRepo;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CashOutflowService {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final CashOutflowRepo cashOutflowRepo;

    public CashOutflowService(CashOutflowRepo cashOutflowRepo) {
        this.cashOutflowRepo = cashOutflowRepo;
    }

    public CashOutflowModel save(CashOutflowModel cashOutflowModel) {
        Date date = new Date();
        String nowDate = dateFormat.format(date);
        long a=date.getTime();
        cashOutflowModel.setDate(nowDate);
        cashOutflowModel.setDateLong(a);
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
}
