package org.nure.gas_station.controllers;

import org.nure.gas_station.controllers.commons.ExchangeValidator;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.exchange_models.gas_station_user_controller.AuthToken;
import org.nure.gas_station.exchange_models.gas_station_user_controller.ChangePassword;
import org.nure.gas_station.exchange_models.gas_station_user_controller.CreateUserCredentials;
import org.nure.gas_station.exchange_models.gas_station_user_controller.UserCredentials;
import org.nure.gas_station.services.interfaces.IUserService;
import org.nure.gas_station.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class GasStationUserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private ExchangeValidator exchangeValidator;

    @RequestMapping(value = "/signin", method = { RequestMethod.POST })
    public ResponseEntity signin(@RequestBody CreateUserCredentials credentials) {
        exchangeValidator.validateConstrains(credentials);
        userService.createUser(credentials.getUsername(), credentials.getPassword(), credentials.getName(), credentials.getSurname(), UserRoles.ROLE_BUYER);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ResponseEntity<AuthToken> login(@RequestBody UserCredentials credentials) {
        exchangeValidator.validateConstrains(credentials);
        Authentication auth = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @Secured({"ROLE_BUYER", "ROLE_ADMIN"})
    @RequestMapping(value = "/change-password", method = { RequestMethod.POST })
    public ResponseEntity changePassword(@RequestBody ChangePassword changePassword) {
        exchangeValidator.validateConstrains(changePassword);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.changePassword(auth.getName(), changePassword.getPassword(), changePassword.getOldPassword());
        return ResponseEntity.ok().build();
    }
}
