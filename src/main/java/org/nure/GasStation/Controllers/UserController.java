package org.nure.GasStation.Controllers;

import org.nure.GasStation.Model.ExchangeModels.UserCredentials;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

//    @Autowired
//    private IUserService userService;

    @RequestMapping(value = "/signin", method = { RequestMethod.POST })
    public ResponseEntity<?> signin(@RequestBody UserCredentials credentials) {
        return ResponseEntity.ok("This is signin!");
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        return ResponseEntity.ok("This is login!");
    }
}
