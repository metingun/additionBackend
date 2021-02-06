package com.website.backend.controller;

import com.website.backend.model.ProductModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restful/product", produces = "application/json")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody ProductModel productModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.save(productModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseModel saveAll(@RequestBody List<ProductModel> productModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.saveAll(productModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseModel getAll() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.getAllProducts(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getAllByMenuType/menuType={menuType}", method = RequestMethod.GET)
    public ResponseModel getAllByMenuType(@PathVariable int menuType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.getAll(menuType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/deleteData/{productId}", method = RequestMethod.GET)
    public ResponseModel deleteData(@PathVariable int productId) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.deleteProduct(productId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseModel update(@RequestBody ProductModel productModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.update(productModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getOneById/id={id}", method = RequestMethod.GET)
    public ResponseModel getOneById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(productService.getOneById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
