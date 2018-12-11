package org.nure.gas_station.controllers;

import org.nure.gas_station.controllers.commons.ExchangeValidator;
import org.nure.gas_station.exchange_models.ListDTO;
import org.nure.gas_station.exchange_models.fuel_controller.*;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    @Autowired
    private IFuelService fuelService;

    @Autowired
    private ExchangeValidator exchangeValidator;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ResponseEntity addFuel(@RequestBody CreateFuel fuelDetails) {
        exchangeValidator.validateConstrains(fuelDetails);
        fuelService.addFuel(
                fuelDetails.getFuelName(),
                fuelDetails.getTariffId(),
                fuelDetails.getFuelLeft());
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/remove", method = RequestMethod.DELETE)
    public ResponseEntity removeFuel(@RequestBody RequestFuel requestFuel) {
        exchangeValidator.validateConstrains(requestFuel);
        fuelService.removeFuel(requestFuel.getFuelName());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/get", method = RequestMethod.POST)
    public ResponseEntity<FuelDetails> getFuel(@RequestBody RequestFuel requestFuel) {
        exchangeValidator.validateConstrains(requestFuel);
        Fuel foundFuel = fuelService.getFuel(requestFuel.getFuelName());
        return ResponseEntity.ok(new FuelDetails(foundFuel));
    }

    @RequestMapping(value="/get-all", method = RequestMethod.GET)
    public ResponseEntity<ListDTO<FuelDetails>> getFuels() {
        List<FuelDetails> foundFuels = fuelService
                .getFuels()
                .stream()
                .map(FuelDetails::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListDTO<FuelDetails>(foundFuels));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/update-name", method = RequestMethod.PUT)
    public ResponseEntity updateFuelName(@RequestBody ChangeFuelName changeFuelName) {
        exchangeValidator.validateConstrains(changeFuelName);
        fuelService.updateFuelName(changeFuelName.getFuelName(), changeFuelName.getNextFuelName());
        return ResponseEntity.status(204).build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/update-tariff", method = RequestMethod.PUT)
    public ResponseEntity updateFuelTariff(@RequestBody FuelTariffDTO fuelTariffDTO) {
        exchangeValidator.validateConstrains(fuelTariffDTO);
        fuelService.updateFuelTariff(fuelTariffDTO.getFuelName(), fuelTariffDTO.getTariffId());
        return ResponseEntity.status(204).build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/update-left", method = RequestMethod.PUT)
    public ResponseEntity updateFuelLeft(@RequestBody FuelAmountDTO fuelAmountDTO) {
        exchangeValidator.validateConstrains(fuelAmountDTO);
        fuelService.updateFuelLeft(fuelAmountDTO.getFuelName(), fuelAmountDTO.getFuelLeft());
        return ResponseEntity.status(204).build();
    }
}
