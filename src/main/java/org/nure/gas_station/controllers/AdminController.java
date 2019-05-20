package org.nure.gas_station.controllers;

import org.nure.gas_station.controllers.commons.ExchangeValidator;
import org.nure.gas_station.exchange_models.PageDTO;
import org.nure.gas_station.exchange_models.admin_controller.ChangeUserRole;
import org.nure.gas_station.exchange_models.admin_controller.SearchUser;
import org.nure.gas_station.exchange_models.admin_controller.UserDetails;
import org.nure.gas_station.exchange_models.ListDTO;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.services.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ExchangeValidator exchangeValidator;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/search-user", method = { RequestMethod.POST })
    public ResponseEntity<PageDTO<UserDetails>> searchUser(@RequestBody SearchUser user) {
        exchangeValidator.validateConstrains(user);
        Page<GasStationUser> users = adminService.searchForUser(user.getUsername(), user.getAmount(), user.getPage());
        List<UserDetails> userDetails = users
                .getContent()
                .stream()
                .map(UserDetails::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageDTO<UserDetails>(userDetails, users.getPageable(), users.getTotalElements()));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/set-role", method = { RequestMethod.POST })
    public ResponseEntity setRole(@RequestBody ChangeUserRole changeUserRole) {
        exchangeValidator.validateConstrains(changeUserRole);
        adminService.setRole(changeUserRole.getUsername(), changeUserRole.getUserRole());
        return ResponseEntity.ok().build();
    }
}
