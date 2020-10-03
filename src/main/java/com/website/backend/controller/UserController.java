package com.website.backend.controller;

import com.website.backend.model.ChangePasswordModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.model.UserModel;
import com.website.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/restful/user", produces = "application/json")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseModel create(@RequestBody UserModel userModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(userService.createUser(userModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseModel login(@RequestBody UserModel userModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(userService.login(userModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseModel changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(userService.changePassword(changePasswordModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSession", method = RequestMethod.POST)
    public ResponseModel getSession(@RequestBody UserModel userModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(userService.getSession(userModel).getSession(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
