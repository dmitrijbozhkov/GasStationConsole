package org.nure.GasStation.Controllers;

import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.RepositoryInterfaces.IFuelRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

//    @Autowired
//    private IFuelService fuelService;

    @Secured("ROLE_BYER")
    @RequestMapping(value="/secured", method = RequestMethod.GET)
    public String secured() {
        return UserRoles.ROLE_BYER.getAuthority();
    }
}
