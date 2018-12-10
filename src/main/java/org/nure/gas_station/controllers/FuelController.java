package org.nure.GasStation.Controllers;

import org.nure.GasStation.Exceptions.InputDataValidationException;
import org.nure.GasStation.Model.ExchangeModels.FuelController.FuelDetails;
import org.nure.GasStation.Model.ExchangeModels.FuelController.FuelList;
import org.nure.GasStation.Model.ExchangeModels.FuelController.RequestFuel;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
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

    private void validateFuelDetails(FuelDetails fuelDetails) {
        if (fuelDetails.getFuelName() == null || fuelDetails.getFuelName().isEmpty()) {
            throw new InputDataValidationException("Fuel name can't be empty");
        }
        if (fuelDetails.getPrice() == 0) {
            throw new InputDataValidationException("Price can't be 0");
        }
    }

    private void validateRequestFuel(RequestFuel requestFuel) {
        if (requestFuel.getFuelName() == null || requestFuel.getFuelName().isEmpty()) {
            throw new InputDataValidationException("Fuel name can't be empty");
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ResponseEntity addFuel(@RequestBody FuelDetails fuelDetails) {
        validateFuelDetails(fuelDetails);
        fuelService.addFuel(
                fuelDetails.getFuelName(),
                fuelDetails.getPrice(),
                fuelDetails.getFuelLeft(),
                fuelDetails.getMaxFuel(),
                fuelDetails.getDescription());
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/remove", method = RequestMethod.DELETE)
    public ResponseEntity removeFuel(@RequestBody RequestFuel requestFuel) {
        validateRequestFuel(requestFuel);
        fuelService.removeFuel(requestFuel.getFuelName());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/get", method = RequestMethod.POST)
    public ResponseEntity<FuelDetails> getFuel(@RequestBody RequestFuel requestFuel) {
        validateRequestFuel(requestFuel);
        Fuel foundFuel = fuelService.getFuel(requestFuel.getFuelName());
        return ResponseEntity.ok(new FuelDetails(foundFuel.getFuelName(), foundFuel.getPrice(), foundFuel.getFuelLeft(), foundFuel.getMaxFuel(), foundFuel.getDescription()));
    }

    @RequestMapping(value="/get-all", method = RequestMethod.GET)
    public ResponseEntity<FuelList> getFuels() {
        List<Fuel> foundFuels = fuelService.getFuels();
        return ResponseEntity.ok(new FuelList(foundFuels
                .stream()
                .map(f -> {
                    return new FuelDetails(f.getFuelName(), f.getPrice(), f.getFuelLeft(), f.getMaxFuel(), f.getDescription());
                })
                .collect(Collectors.toList())));
    }
}
