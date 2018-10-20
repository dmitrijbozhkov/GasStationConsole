package org.nure.GasStation.Controllers;

import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.ExchangeModels.AuthToken;
import org.nure.GasStation.Model.ExchangeModels.UserCredentials;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.nure.GasStation.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @RequestMapping(value = "/signin", method = { RequestMethod.POST })
    public ResponseEntity signin(@RequestBody UserCredentials credentials) {
        userService.createUser(credentials.getUsername(), credentials.getPassword(), UserRoles.ROLE_BYER);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ResponseEntity<AuthToken> login(@RequestBody UserCredentials credentials) {
        Authentication auth = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new AuthToken(token));
    }

    public ResponseEntity changePassword() {

    }
}
