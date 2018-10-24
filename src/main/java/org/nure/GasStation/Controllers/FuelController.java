package org.nure.GasStation.Controllers;

import org.nure.GasStation.Exceptions.InputDataValidationException;
import org.nure.GasStation.Model.ExchangeModels.FuelController.CreateFuel;
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

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    @Autowired
    private IFuelService fuelService;

    private void validateCreateFuel(CreateFuel createFuel) {
        if (createFuel.getFuelName() == null || createFuel.getFuelName().isEmpty()) {
            throw new InputDataValidationException("Fuel name can't be empty");
        }
        if (createFuel.getPrice() == 0) {
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
    public ResponseEntity addFuel(@RequestBody CreateFuel createFuel) {
        validateCreateFuel(createFuel);
        fuelService.addFuel(createFuel.getFuelName(), createFuel.getPrice(), createFuel.getFuelLeft());
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/remove", method = RequestMethod.DELETE)
    public ResponseEntity removeFuel(@RequestBody RequestFuel requestFuel) {
        validateRequestFuel(requestFuel);
        fuelService.removeFuel(requestFuel.getFuelName());
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_BYER"})
    @RequestMapping(value="/get", method = RequestMethod.POST)
    public ResponseEntity<Fuel> getFuel(@RequestBody RequestFuel requestFuel) {
        validateRequestFuel(requestFuel);
        Fuel foundFuel = fuelService.getFuel(requestFuel.getFuelName());
        return ResponseEntity.ok(foundFuel);
    }

    @Secured({"ROLE_ADMIN", "ROLE_BYER"})
    @RequestMapping(value="/get-all", method = RequestMethod.GET)
    public ResponseEntity<FuelList> getFuels() {
        List<Fuel> foundFuels = fuelService.getFuels();
        return ResponseEntity.ok(new FuelList(foundFuels));
    }
}
