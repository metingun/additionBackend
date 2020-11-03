package com.website.backend.controller;

import com.website.backend.model.CashOutflowModel;
import com.website.backend.model.PersonelModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.CashOutflowService;
import com.website.backend.service.PersonelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseModel update(@RequestBody CashOutflowModel cashOutflowModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.update(cashOutflowModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseModel saveAll(@RequestBody List<CashOutflowModel> cashOutflowModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.saveAll(cashOutflowModel), false);
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

    @RequestMapping(value = "/getCashOutflowById/id={id}", method = RequestMethod.GET)
    public ResponseModel getCashOutflowById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getCashOutflowById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAllByDate/startDate={startDate}/finishDate={finishDate}", method = RequestMethod.GET)
    public ResponseModel getAllByDate(@PathVariable String startDate,@PathVariable String finishDate) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getAllByDate(startDate,finishDate), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getTotalPersonalByDate/startDate={startDate}/finishDate={finishDate}", method = RequestMethod.GET)
    public ResponseModel getTotalPersonalByDate(@PathVariable String startDate,@PathVariable String finishDate) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getTotalPersonalByDate(startDate,finishDate), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getCashflowByDateAndPersonelId/startDate={startDate}/finishDate={finishDate}/personelId={personelId}", method = RequestMethod.GET)
    public ResponseModel getCashflowByDateAndPersonelId(@PathVariable String startDate,@PathVariable String finishDate,@PathVariable long personelId) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getCashflowByDateAndPersonelId(startDate,finishDate,personelId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getTotalsByOutcomeType/startDate={startDate}/finishDate={finishDate}", method = RequestMethod.GET)
    public ResponseModel getTotalsByOutcomeType(@PathVariable String startDate,@PathVariable String finishDate) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(cashOutflowService.getTotalsByOutcomeType(startDate,finishDate), false);
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
