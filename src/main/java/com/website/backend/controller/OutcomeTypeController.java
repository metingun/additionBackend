package com.website.backend.controller;

import com.website.backend.model.OutcomeTypeModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.OutcomeTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restful/outcomeType", produces = "application/json")
public class OutcomeTypeController {

    private final OutcomeTypeService outcomeTypeService;

    public OutcomeTypeController(OutcomeTypeService outcomeTypeService) {
        this.outcomeTypeService = outcomeTypeService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody OutcomeTypeModel outcomeTypeModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(outcomeTypeService.save(outcomeTypeModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseModel saveAll(@RequestBody List<OutcomeTypeModel> outcomeTypeModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(outcomeTypeService.saveAll(outcomeTypeModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(outcomeTypeService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(outcomeTypeService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
