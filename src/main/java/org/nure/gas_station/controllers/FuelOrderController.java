package org.nure.gas_station.controllers;

import org.nure.gas_station.controllers.commons.ExchangeValidator;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.exchange_models.PageDTO;
import org.nure.gas_station.exchange_models.fuel_order_controller.order.CreateOrder;
import org.nure.gas_station.exchange_models.fuel_order_controller.order.OrderDetails;
import org.nure.gas_station.exchange_models.fuel_order_controller.query.*;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelsVolumeOfSales;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelsVolumeOfSalesRequest;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.VolumeOfSalesRequest;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.services.interfaces.IFuelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class FuelOrderController {

    @Autowired
    private IFuelOrderService fuelOrderService;

    @Autowired
    private ExchangeValidator exchangeValidator;

    private ResponseEntity<PageDTO<OrderDetails>> buildResponsePage(Page<FuelOrder> operations) {
        List<OrderDetails> orderDetails = operations
                .getContent()
                .stream()
                .map(o -> new OrderDetails(o))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageDTO<OrderDetails>(orderDetails, operations.getPageable(), operations.getTotalElements()));
    }

    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ResponseEntity createOrder(@RequestBody CreateOrder createOrder) throws OperationException {
        exchangeValidator.validateConstrains(createOrder);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        fuelOrderService.orderFuel(
                auth.getName(),
                createOrder.getFuelName(),
                createOrder.getAmount(),
                createOrder.getOrderType(),
                createOrder.getOrderDate());
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
    @RequestMapping(value = "/get/{id}", method = { RequestMethod.GET })
    public ResponseEntity<OrderDetails> getOperation(@PathVariable("id") long id) {
        FuelOrder fuelOrder = fuelOrderService.getOrderById(id);
        return ResponseEntity.ok(new OrderDetails(fuelOrder));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTariff(@PathVariable("id") long id) {
        fuelOrderService.removeFuelOrder(id);
        return ResponseEntity.status(204).build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/query", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryOrders(@RequestBody QueryOrders queryOrders) {
        exchangeValidator.validateConstrains(queryOrders);
        Page<FuelOrder> orders = fuelOrderService.getOrders(
                queryOrders.getAmount(),
                queryOrders.getPage());
        return buildResponsePage(orders);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/query-user", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryUserOrders(@RequestBody QueryUserOrders queryUserOrders) {
        exchangeValidator.validateConstrains(queryUserOrders);
        Page<FuelOrder> orders = fuelOrderService.getUserOrders(
                queryUserOrders.getUsername(),
                queryUserOrders.getAmount(),
                queryUserOrders.getPage());
        return buildResponsePage(orders);
    }

    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
    @RequestMapping(value = "/query-user-current", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryUserCurrentOrders(@RequestBody QueryOrders queryOrders) {
        exchangeValidator.validateConstrains(queryOrders);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Page<FuelOrder> orders = fuelOrderService.getUserOrders(
                auth.getName(),
                queryOrders.getAmount(),
                queryOrders.getPage());
        return buildResponsePage(orders);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/query-date", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryDateOrders(@RequestBody QueryDateOrders queryDateOrders) {
        exchangeValidator.validateConstrains(queryDateOrders);
        Page<FuelOrder> operations = fuelOrderService.getTimeOrders(
                queryDateOrders.getBefore(),
                queryDateOrders.getAfter(),
                queryDateOrders.getAmount(),
                queryDateOrders.getPage());
        return buildResponsePage(operations);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/query-fuel", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryFuelOrders(@RequestBody QueryFuelOrders queryFuelOrders) {
        Page<FuelOrder> operations = fuelOrderService.getFuelOrders(
                queryFuelOrders.getFuelName(),
                queryFuelOrders.getAmount(),
                queryFuelOrders.getPage());
        return buildResponsePage(operations);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/query-fuel-date", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<OrderDetails>> queryFuelDateOrders(@RequestBody QueryFuelDateOrders queryFuelDateOrders) {
        Page<FuelOrder> operations = fuelOrderService.getFuelTimeOrders(
                queryFuelDateOrders.getFuelName(),
                queryFuelDateOrders.getBefore(),
                queryFuelDateOrders.getAfter(),
                queryFuelDateOrders.getAmount(),
                queryFuelDateOrders.getPage());
        return buildResponsePage(operations);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/volume-of-sales", method = { RequestMethod.POST })
    public ResponseEntity<FuelsVolumeOfSales> getVolumeOfSales(@RequestBody VolumeOfSalesRequest volumeOfSalesRequest) {
         FuelsVolumeOfSales fuelsVolumeOfSales = fuelOrderService.getVolumeOfSalesBetweenDates(
                volumeOfSalesRequest.getBefore(),
                volumeOfSalesRequest.getAfter());
        return ResponseEntity.ok(fuelsVolumeOfSales);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/volume-of-sales-fuel", method = { RequestMethod.POST })
    public ResponseEntity<FuelsVolumeOfSales> getVolumeOfSalesForFuels(@RequestBody FuelsVolumeOfSalesRequest fuelsVolumeOfSalesRequest) {
        FuelsVolumeOfSales fuelsVolumeOfSales = fuelOrderService.getFuelsVolumeOfSalesBetweenDates(
                fuelsVolumeOfSalesRequest.getFuelNames(),
                fuelsVolumeOfSalesRequest.getBefore(),
                fuelsVolumeOfSalesRequest.getAfter());
        return ResponseEntity.ok(fuelsVolumeOfSales);
    }
}
