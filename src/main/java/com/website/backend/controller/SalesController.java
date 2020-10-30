package com.website.backend.controller;

import com.website.backend.model.CancelSaleModel;
import com.website.backend.model.SalesModel;
import com.website.backend.model.ResponseModel;
import com.website.backend.service.SalesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restful/sales", produces = "application/json")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseModel save(@RequestBody List<SalesModel> sales) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.save(sales), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getActiveSalesByTableName/tableName={tableName}", method = RequestMethod.GET)
    public ResponseModel getActiveSalesByTableName(@PathVariable String tableName) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getActiveSalesByTableName(tableName), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSaleByRayon/startDate={startDate}/finishDate={finishDate}", method = RequestMethod.GET)
    public ResponseModel getSaleByRayon(@PathVariable String startDate,@PathVariable String finishDate) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getSaleByRayon(startDate,finishDate), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/favouriteProductsList/sortType={sortType}", method = RequestMethod.GET)
    public ResponseModel favouriteProductsList(@PathVariable int sortType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.favouriteProductsList(sortType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSaleListByRayon/startDate={startDate}/finishDate={finishDate}/categoryType={categoryType}", method = RequestMethod.GET)
    public ResponseModel getSaleListByRayon(@PathVariable String startDate,@PathVariable String finishDate,@PathVariable int categoryType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getSaleListByRayon(startDate,finishDate,categoryType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSalesByCompleteOrderAndOrderStatusAndCancelSales/completeOrder={completeOrder}/orderStatus={orderStatus}/cancelSales={cancelSales}/categoryType={categoryType}", method = RequestMethod.GET)
    public ResponseModel getSalesByCompleteOrderAndOrderStatusAndCancelSales(@PathVariable int completeOrder,@PathVariable int orderStatus,@PathVariable int cancelSales,@PathVariable int categoryType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getSalesByCompleteOrderAndOrderStatusAndCancelSales(completeOrder,orderStatus,cancelSales,categoryType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSalesByCompleteOrderAndOrderStatusAndCancelSalesAndTableName/completeOrder={completeOrder}/orderStatus={orderStatus}/cancelSales={cancelSales}/tableName={tableName}", method = RequestMethod.GET)
    public ResponseModel getSalesByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(@PathVariable int completeOrder,@PathVariable int orderStatus,@PathVariable int cancelSales,@PathVariable String tableName) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getSalesByCompleteOrderAndOrderStatusAndCancelSalesAndTableName(completeOrder,orderStatus,cancelSales,tableName), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getSalesAllStatusZeroOneDay/categoryType={categoryType}", method = RequestMethod.GET)
    public ResponseModel getSalesAllStatusZeroOneDay(@PathVariable int categoryType) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getSalesByCompleteOrderAndOrderStatusAndCancelSales(0,0,0,categoryType), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/setSalesStartDateById/id={id}", method = RequestMethod.GET)
    public ResponseModel setSalesStartDateById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.setSalesStartDateById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/setSalesFinishDateById/id={id}", method = RequestMethod.GET)
    public ResponseModel setSalesFinishDateById(@PathVariable long id) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.setSalesFinishDateById(id), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/getCancelSales", method = RequestMethod.GET)
    public ResponseModel getCancelSales() {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.getCancelSales(), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/cancelSaleCheck/salesId={salesId}", method = RequestMethod.GET)
    public ResponseModel cancelSaleCheck(@PathVariable long salesId) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.cancelSaleCheck(salesId), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }

    @RequestMapping(value = "/cancelSale", method = RequestMethod.POST)
    public ResponseModel cancelSale(@RequestBody CancelSaleModel cancelSaleModel) {
        try {
            return ResponseModel
                    .createSuccessResponseWithData(salesService.cancelSale(cancelSaleModel), false);
        } catch (Exception e) {
            return ResponseModel.createErrorResponseWithErrorMessage(e);
        }
    }
}
