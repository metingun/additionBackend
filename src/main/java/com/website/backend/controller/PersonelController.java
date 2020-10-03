package com.website.backend.controller;

import com.website.backend.model.PersonelModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.PersonelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restful/personel", produces = "application/json")
public class PersonelController {

    private final PersonelService personelService;

    public PersonelController(PersonelService personelService) {
        this.personelService = personelService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody PersonelModel personelModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(personelService.save(personelModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(personelService.getAll(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.GET)
    public ResponseModel delete(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(personelService.delete(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
