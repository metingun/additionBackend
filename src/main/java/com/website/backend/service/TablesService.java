package com.website.backend.service;

import com.website.backend.model.CompanyModel;
import com.website.backend.model.TableTransferModel;
import com.website.backend.model.TablesModel;
import com.website.backend.repository.CompanyRepo;
import com.website.backend.repository.TablesRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TablesService {

    private final TablesRepo tablesRepo;
    private final CompanyRepo companyRepo;
    private final AdditionService additionService;
    private final SalesService salesService;

    public TablesService(TablesRepo tablesRepo,CompanyRepo companyRepo,AdditionService additionService,SalesService salesService) {
        this.tablesRepo = tablesRepo;
        this.companyRepo = companyRepo;
        this.additionService = additionService;
        this.salesService = salesService;
    }

    public TablesModel save(TablesModel tablesModel,long companyId) {
        if (tablesRepo.findByTableName(tablesModel.getTableName())!=null){
            return null;
        }
        if (companyId==-1){
            tablesModel.setTableType("Dış Siparişler");
            tablesModel.setMenuType(1);
        }
        else if(companyId==-2){
            String[] parts = tablesModel.getTableName().split("\\|\\|");
            tablesModel.setTableType(parts[0]);
            tablesModel.setMenuType(1);
        }
        else{
            CompanyModel company=companyRepo.findById(companyId);
            tablesModel.setTableType(company.getCompanyName());
            tablesModel.setMenuType(company.getMenuType());
        }

        tablesRepo.save(tablesModel);
        return tablesModel;
    }

    public String delete(long id) {
        tablesRepo.delete(tablesRepo.findById(id));
        return "200";
    }

    public List<TablesModel> getAll() {
        return tablesRepo.findAll();
    }

    public TablesModel getTableByName(String name) {
        return tablesRepo.findByTableName(name);
    }

    public String saveAll(List<TablesModel> tablesModels) {
        tablesRepo.saveAll(tablesModels);
        return "Successful";
    }

    public String getTableNameById(long id) {
        return tablesRepo.findById(id).getTableName();
    }

    public int getMenuTypeByTableName(String tableName) {
        return tablesRepo.findByTableName(tableName).getMenuType();
    }

    public List<TablesModel> getAllByMenuType(int menuType) {
        List<TablesModel> tablesModels=tablesRepo.findAllByMenuType(menuType);
        List<TablesModel> tablesModels1=new ArrayList<>();
        for (TablesModel tableModel:tablesModels) {
            if (tableModel.getPayment()!=0){
                tablesModels1.add(tableModel);
            }
        }
        return tablesModels1;
    }

    public List<TablesModel> getAllDataByCompanyId(long companyId) {
        String companyName=companyRepo.findById(companyId).getCompanyName();
        return tablesRepo.findAllByTableType(companyName);
    }

    public void tableTransfer(TablesModel toTable,TablesModel fromTable) {
        fromTable.setPayment(0);
        toTable.setPayment(additionService.getOneAdditionByTableNameAndActivity(toTable).getPayment());
        tablesRepo.save(fromTable);
        tablesRepo.save(toTable);
    }

    public String tableTransferAll(TableTransferModel tableTransferModel) {
        TablesModel toTable=tablesRepo.findByTableName(tableTransferModel.getToTable());
        TablesModel fromTable=tablesRepo.findByTableName(tableTransferModel.getFromTable());
        if (toTable.getPayment()==0){
            salesService.salesTransferToEmptyTable(tableTransferModel);
            additionService.additionTransferToEmptyTable(tableTransferModel);
        }
        else {
            salesService.salesTransfer(tableTransferModel);
            additionService.additionTransfer(tableTransferModel);
        }
        tableTransfer(toTable,fromTable);
        return "Successfully";
    }
}
