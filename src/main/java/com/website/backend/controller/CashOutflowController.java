package com.website.backend.controller;

import com.website.backend.model.CashOutflowModel;
import com.website.backend.model.PersonelModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.CashOutflowService;
import com.website.backend.service.PersonelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restful/cashOutflow", produces = "application/json")
public class CashOutflowController {

    private final CashOutflowService cashOutflowService;

    public CashOutflowController(CashOutflowService cashOutflowService) {
        this.cashOutflowService = cashOutflowService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody CashOutflowModel cashOutflowModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.save(cashOutflowModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

/*    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }*/
}
