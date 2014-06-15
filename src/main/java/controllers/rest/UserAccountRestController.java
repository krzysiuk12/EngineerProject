package controllers.rest;

import annotations.NotAuthorized;
import domain.locations.Location;
import domain.useraccounts.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Controller(value = "userAccountRestController")
@RequestMapping(value = "/users")
public class UserAccountRestController {

    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;

    @Autowired
    public UserAccountRestController(IUserManagementService userManagementService, ILocationManagementService locationManagementService) {
        this.userManagementService = userManagementService;
        this.locationManagementService = locationManagementService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<UserAccount> getAllUserAccounts(@RequestHeader(value = "authorization") String token) {
        //TODO: Method only for admin - different authorization
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @NotAuthorized
    public @ResponseBody List<UserAccount> addNewUserAccount(@RequestBody String data) {
        //TODO: Create Service and repository method
        return null;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody UserAccount getUserAccountById(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) {
        UserAccount userAccount = userManagementService.getUserAccountById(userId);
        userAccount.setIndividual(null);
        return userAccount;
    }

    @RequestMapping(value = "/{userId}/all", method = RequestMethod.GET)
    public @ResponseBody UserAccount getUserAccountByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) {
        return userManagementService.getUserAccountByIdAllData(userId);
    }

    @RequestMapping(value = "/{userId}/locations", method = RequestMethod.GET)
    public @ResponseBody List<Location> getAllUsersPrivateLocations(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) {
        return locationManagementService.getAllUsersPrivateLocations(userId);
    }
}
