package controllers.rest;

import annotations.AdminAuthorized;
import annotations.NotAuthorized;
import domain.locations.Location;
import domain.useraccounts.UserAccount;
import jsonserializers.common.ResponseSerializer;
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

    @NotAuthorized
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer addNewUserAccount(@RequestBody UserAccount data) throws Exception {
        //TODO: Create Service and repository method
        return null;
    }

    @AdminAuthorized
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getUserAccountById(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountById(userId);
        userAccount.setIndividual(null);
        return new ResponseSerializer(userAccount);
    }

    @AdminAuthorized
    @RequestMapping(value = "/{userId}/all", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getUserAccountByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        return new ResponseSerializer(userManagementService.getUserAccountByIdAllData(userId));
    }

    @AdminAuthorized
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<UserAccount>> getAllUserAccounts(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(userManagementService.getAllUserAccounts());
    }

    @RequestMapping(value = "/users/my", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getMyUserAccountData(@RequestHeader(value = "authorization") String token) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountByToken(token);
        userAccount.setIndividual(null);
        return new ResponseSerializer(userAccount);
    }

    @RequestMapping(value = "/users/my/all", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getMyUserAccountAllData(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        return new ResponseSerializer(userManagementService.getUserAccountByIdAllData(userId));
    }

    @RequestMapping(value = "/{userId}/locations", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<Location>> getAllUsersPrivateLocations(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        return new ResponseSerializer(locationManagementService.getAllUsersPrivateLocations(userId));
    }
}
