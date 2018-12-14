package org.nure.gas_station.controllers;

import org.nure.gas_station.controllers.commons.ExchangeValidator;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.exchange_models.fuel_controller.CreateFuel;
import org.nure.gas_station.exchange_models.fuel_controller.FuelDetails;
import org.nure.gas_station.exchange_models.fuel_controller.RequestFuel;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.CreateTariff;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffDetails;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffFuelDetails;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tariff")
public class FuelTariffController {

    @Autowired
    private ExchangeValidator exchangeValidator;

    @Autowired
    private IFuelTariffService fuelTariffService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ResponseEntity<TariffDetails> addTariff(@RequestBody CreateTariff createTariff) {
        exchangeValidator.validateConstrains(createTariff);
        FuelTariff tariff = fuelTariffService.createFuelTariff(createTariff.getExchangeRate());
        return ResponseEntity.ok(new TariffDetails(tariff));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeTariff(@PathVariable("id") long id) throws OperationException {
        fuelTariffService.removeFuelTariff(id);
        return ResponseEntity.status(204).build();
    }

    @RequestMapping(value="/get/{id}", method = RequestMethod.POST)
    public ResponseEntity<TariffFuelDetails> getTariff(@PathVariable("id") long id) {
        FuelTariff foundTariff = fuelTariffService.getFuelTariff(id);
        return ResponseEntity.ok(new TariffFuelDetails(foundTariff));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/update", method = RequestMethod.PUT)
    public ResponseEntity updateTariff(@RequestBody TariffDetails tariffDetails) {
        exchangeValidator.validateConstrains(tariffDetails);
        fuelTariffService.updateFuelTariff(
                tariffDetails.getId(),
                tariffDetails.getExchangeRate());
        return ResponseEntity.status(204).build();
    }

}
