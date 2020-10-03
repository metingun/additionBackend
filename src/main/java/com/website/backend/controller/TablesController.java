package com.website.backend.controller;

import com.website.backend.model.ResponseModel;
import com.website.backend.model.TablesModel;
import com.website.backend.service.TablesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restful/tables", produces = "application/json")
public class TablesController {

    private final TablesService tablesService;

    public TablesController(TablesService tablesService) {
        this.tablesService = tablesService;
    }

    @RequestMapping(value = "/save/companyId={companyId}", method = RequestMethod.POST)
    public ResponseModel save(@PathVariable long companyId,@RequestBody TablesModel tablesModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.save(tablesModel,companyId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getTableNameById/id={id}", method = RequestMethod.GET)
    public ResponseModel getTableNameById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.getTableNameById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getMenuTypeByTableName/tableName={tableName}", method = RequestMethod.GET)
    public ResponseModel getMenuTypeByTableName(@PathVariable String tableName) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.getMenuTypeByTableName(tableName), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAllByMenuType/menuType={menuType}", method = RequestMethod.GET)
    public ResponseModel getAllByMenuType(@PathVariable int menuType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.getAllByMenuType(menuType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAllDataByCompanyId/companyId={companyId}", method = RequestMethod.GET)
    public ResponseModel getAllDataByCompanyId(@PathVariable long companyId) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.getAllDataByCompanyId(companyId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(tablesService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
