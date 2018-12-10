package org.nure.GasStation.Controllers;

import org.nure.GasStation.Exceptions.InputDataValidationException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.AuthToken;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.ChangePassword;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.CreateUserCredentials;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.UserCredentials;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.nure.GasStation.Security.JwtTokenProvider;
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

    private void validateUserCredentials(UserCredentials userCredentials) {
        if (userCredentials.getUsername() == null || userCredentials.getUsername().isEmpty()) {
            throw new InputDataValidationException("Username can't be empty");
        }
        if (userCredentials.getPassword() == null || userCredentials.getPassword().isEmpty()) {
            throw new InputDataValidationException("Password can't be empty");
        }
    }

    private void validatePasswordChange(ChangePassword changePassword) {
        if (changePassword.getOldPassword() == null || changePassword.getOldPassword().isEmpty()) {
            throw new InputDataValidationException("Old password can't be empty");
        }
        if (changePassword.getPassword() == null || changePassword.getPassword().isEmpty()) {
            throw new InputDataValidationException("Password can't be empty");
        }
        if (changePassword.getPassword().equals(changePassword.getOldPassword())) {
            throw new InputDataValidationException("New and old passwords can't be the same");
        }
    }

    @RequestMapping(value = "/signin", method = { RequestMethod.POST })
    public ResponseEntity signin(@RequestBody CreateUserCredentials credentials) {
        validateUserCredentials(credentials);
        userService.createUser(credentials.getUsername(), credentials.getPassword(), credentials.getName(), credentials.getSurname(), UserRoles.ROLE_BYER);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ResponseEntity<AuthToken> login(@RequestBody UserCredentials credentials) {
        validateUserCredentials(credentials);
        Authentication auth = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @Secured({"ROLE_BUYER", "ROLE_ADMIN"})
    @RequestMapping(value = "/change-password", method = { RequestMethod.POST })
    public ResponseEntity changePassword(@RequestBody ChangePassword changePassword) {
        validatePasswordChange(changePassword);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.changePassword(auth.getName(), changePassword.getPassword(), changePassword.getOldPassword());
        return ResponseEntity.ok().build();
    }
}
