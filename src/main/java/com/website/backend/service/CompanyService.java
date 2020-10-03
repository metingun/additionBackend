package com.website.backend.service;

import com.website.backend.model.CompanyModel;
import com.website.backend.model.TablesModel;
import com.website.backend.repository.CompanyRepo;
import com.website.backend.repository.SalesRepo;
import com.website.backend.repository.TablesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final TablesRepo tablesRepo;
    private final SalesRepo salesRepo;

    public CompanyService(CompanyRepo companyRepo,TablesRepo tablesRepo,SalesRepo salesRepo) {
        this.companyRepo = companyRepo;
        this.tablesRepo = tablesRepo;
        this.salesRepo = salesRepo;
    }

    public CompanyModel save(CompanyModel companyModel) {
        if (companyRepo.findByCompanyName(companyModel.getCompanyName()) != null) {
            return null;
        }
        companyRepo.save(companyModel);
        return companyModel;
    }

    public String delete(long id) {
        companyRepo.delete(companyRepo.findById(id));
        return "200";
    }

    public List<CompanyModel> getAll() {
        return companyRepo.findAll();
    }

    public int getCompanySaleStatusByCompanyId(long id) {
        List<TablesModel> tableList=tablesRepo.findAllByTableType(companyRepo.findById(id).getCompanyName());
        for (TablesModel table:tableList) {
            if (salesRepo.findAllByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(1,1,0,table.getTableName()).size()!=0){
                return 1;
            }
        }
        return 0;
    }
}
