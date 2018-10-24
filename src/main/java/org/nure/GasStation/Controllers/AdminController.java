package org.nure.GasStation.Controllers;

import org.nure.GasStation.Exceptions.InputDataValidationException;
import org.nure.GasStation.Model.ExchangeModels.AdminController.ChangeUserRole;
import org.nure.GasStation.Model.ExchangeModels.AdminController.SearchUser;
import org.nure.GasStation.Model.ExchangeModels.AdminController.UserDetails;
import org.nure.GasStation.Model.ExchangeModels.GasStationPage;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.ServiceInterfaces.IAdminService;
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

    private void validateSearchUser(SearchUser searchUser) {
        if (searchUser.getUsername() == null || searchUser.getUsername().isEmpty()) {
            throw new InputDataValidationException("Username can't be empty");
        }
        if (searchUser.getAmount() < 1) {
            throw new InputDataValidationException("Amount can't be less than 1");
        }
        if (searchUser.getPage() < 0) {
            throw new InputDataValidationException("Page can't be less than 0");
        }
    }

    private void validateChangeUserRole(ChangeUserRole changeUserRole) {
        if (changeUserRole.getUsername() == null || changeUserRole.getUsername().isEmpty()) {
            throw new InputDataValidationException("Username can't be empty");
        }
        if (changeUserRole.getUserRole() == null) {
            throw new InputDataValidationException("UserRole can't be empty");
        }
    }

//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/get-user", method = { RequestMethod.POST })
//    public ResponseEntity<GasStationUser> getUser(@RequestBody GetUser user) {
//        validateUser(user);
//        return ResponseEntity.ok(adminService.getUser(user.getUsername()));
//    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/search-user", method = { RequestMethod.POST })
    public ResponseEntity<GasStationPage<UserDetails>> searchUser(@RequestBody SearchUser user) {
        validateSearchUser(user);
        Page<GasStationUser> users = adminService.searchForUser(user.getUsername(), user.getAmount(), user.getPage());
        List<UserDetails> usernames = users
                .getContent()
                .stream()
                .map(u -> new UserDetails(u))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new GasStationPage<UserDetails>(usernames, users.getPageable(), users.getTotalElements()));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/set-role", method = { RequestMethod.POST })
    public ResponseEntity setRole(@RequestBody ChangeUserRole changeUserRole) {
        validateChangeUserRole(changeUserRole);
        adminService.setRole(changeUserRole.getUsername(), changeUserRole.getUserRole());
        return ResponseEntity.ok().build();
    }
}
