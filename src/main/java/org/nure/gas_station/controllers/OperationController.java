package org.nure.gas_station.controllers;

import org.nure.gas_station.exceptions.InputDataValidationException;
import org.nure.gas_station.exchange_models.OperationController.*;
import org.nure.gas_station.services.interfaces.IFuelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    @Autowired
    private IFuelOrderService operationService;

    private void validateOperationRequest(OperationRequest operationRequest) {
        if (operationRequest.getFuelName() == null || operationRequest.getFuelName().isEmpty()) {
            throw new InputDataValidationException("Fuel name cannot be empty");
        }
        if (operationRequest.getAmount() < 0.5) {
            throw new InputDataValidationException("Can't make transaction for less than 0.5L of fuel");
        }
    }
//
//    private ResponseEntity<ListDTO<OperationResponse>> buildResponsePage(Page<Operation> operations) {
//        System.out.println(operations);
//        List<OperationResponse> ops = operations
//                .getContent()
//                .stream()
//                .map(o -> new OperationResponse(o))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new ListDTO<OperationResponse>(ops, operations.getPageable(), operations.getTotalElements()));
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
//    @RequestMapping(value = "/buy-fuel", method = { RequestMethod.POST })
//    public ResponseEntity buyFuel(@RequestBody OperationRequest operationRequest) throws OperationException {
//        validateOperationRequest(operationRequest);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        operationService.orderFuel(auth.getName(), operationRequest.getFuelName(), operationRequest.getAmount());
//        return ResponseEntity.ok().build();
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/fill-fuel", method = { RequestMethod.POST })
//    public ResponseEntity fillFuel(@RequestBody OperationRequest operationRequest) throws OperationException {
//        validateOperationRequest(operationRequest);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        operationService.fillFuel(auth.getName(), operationRequest.getFuelName(), operationRequest.getAmount());
//        return ResponseEntity.ok().build();
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
//    @RequestMapping(value = "/get/{operationId}", method = { RequestMethod.GET })
//    public ResponseEntity<OperationResponse> getOperation(@PathVariable("operationId") long operationId) {
//        Operation operation = operationService.getOrderById(operationId);
//        return ResponseEntity.ok(new OperationResponse(operation));
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/query-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryOperations(@RequestBody QueryOperationsPage queryOperations) {
//        Page<Operation> operations = operationService.getOperations(
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/query-user-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryUserOperations(@RequestBody QueryOperationsUser queryOperations) {
//        Page<Operation> operations = operationService.getUserOperations(
//                queryOperations.getUsername(),
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/query-date-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryDateOperations(@RequestBody QueryOperationsDate queryOperations) {
//        Page<Operation> operations = operationService.getTimeOperations(
//                queryOperations.getBefore(),
//                queryOperations.getAfter(),
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/query-user-date-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryUserDateOperations(@RequestBody QueryOperationsUserDate queryOperations) {
//        Page<Operation> operations = operationService.getUserTimeOperations(
//                queryOperations.getUsername(),
//                queryOperations.getBefore(),
//                queryOperations.getAfter(),
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_BUYER"})
//    @RequestMapping(value = "/query-my-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryMyOperations(@RequestBody QueryOperationsPage queryOperations) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Page<Operation> operations = operationService.getUserOperations(
//                auth.getName(),
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
//
//    @Secured({"ROLE_ADMIN", "ROLE_BYER"})
//    @RequestMapping(value = "/query-my-date-pages", method = { RequestMethod.POST })
//    public ResponseEntity<ListDTO<OperationResponse>> queryMyDateOperations(@RequestBody QueryOperationsDate queryOperations) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Page<Operation> operations = operationService.getUserTimeOperations(
//                auth.getName(),
//                queryOperations.getBefore(),
//                queryOperations.getAfter(),
//                queryOperations.getAmount(),
//                queryOperations.getPage());
//        return buildResponsePage(operations);
//    }
}
