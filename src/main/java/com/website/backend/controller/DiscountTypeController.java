package com.website.backend.controller;

import com.website.backend.model.DiscountTypeModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.DiscountTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restful/discountType", produces = "application/json")
public class DiscountTypeController {

    private final DiscountTypeService discountTypeService;

    public DiscountTypeController(DiscountTypeService discountTypeService) {
        this.discountTypeService = discountTypeService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody DiscountTypeModel discountTypeModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(discountTypeService.save(discountTypeModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseModel saveAll(@RequestBody List<DiscountTypeModel> discountTypeModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(discountTypeService.saveAll(discountTypeModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(discountTypeService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(discountTypeService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
