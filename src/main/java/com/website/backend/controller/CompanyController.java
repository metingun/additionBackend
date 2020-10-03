package com.website.backend.controller;

import com.website.backend.model.CompanyModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.CompanyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restful/company", produces = "application/json")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody CompanyModel companyModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(companyService.save(companyModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(companyService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getCompanySaleStatusByCompanyId/id={companyId}", method = RequestMethod.GET)
    public ResponseModel getCompanySaleStatusByCompanyId(@PathVariable long companyId) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(companyService.getCompanySaleStatusByCompanyId(companyId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(companyService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
