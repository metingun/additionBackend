package com.website.backend.controller;

import com.website.backend.model.AdditionModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.model.TablesModel;
import com.website.backend.service.AdditionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restful/addition", produces = "application/json")
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseModel saveAll(@RequestBody List<AdditionModel> additionModels) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.saveAll(additionModels), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAdditionTotalByDate/startDate={startDate}/finishDate={finishDate}", method = RequestMethod.GET)
    public ResponseModel getAdditionTotalByDate(@PathVariable String startDate,@PathVariable String finishDate) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAdditionTotalByDate(startDate,finishDate), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAdditionsByDateAndPaymentType/startDate={startDate}/finishDate={finishDate}/paymentType={paymentType}", method = RequestMethod.GET)
    public ResponseModel getAdditionsByDateAndPaymentType(@PathVariable String startDate,@PathVariable String finishDate,@PathVariable int paymentType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAdditionsByDate(startDate,finishDate,paymentType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAdditionByTableNameAndActivity", method = RequestMethod.POST)
    public ResponseModel getAdditionByTableNameAndActivity(@RequestBody TablesModel tablesModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAdditionByTableNameAndActivity(tablesModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAdditionByTableName", method = RequestMethod.POST)
    public ResponseModel getAdditionByTableName(@RequestBody TablesModel tablesModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAdditionByTableName(tablesModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAdditionById/id={id}", method = RequestMethod.GET)
    public ResponseModel getAdditionById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.getAdditionById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseModel update(@RequestBody AdditionModel additionModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.update(additionModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/payBill", method = RequestMethod.POST)
    public ResponseModel payBill(@RequestBody AdditionModel additionModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(additionService.payBill(additionModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
