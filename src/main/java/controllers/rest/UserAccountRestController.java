package controllers.rest;

import annotations.NotAuthorized;
import domain.useraccounts.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Controller(value = "userAccountRestController")
@RequestMapping(value = "/users")
public class UserAccountRestController {

    private IUserManagementService userManagementService;

    @Autowired
    public UserAccountRestController(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody UserAccount getUserAccountById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        UserAccount userAccount = userManagementService.getUserAccountById(id);
        userAccount.setIndividual(null);
        return userAccount;
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public @ResponseBody UserAccount getUserAccountByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        return userManagementService.getUserAccountByIdAllData(id);
    }
}
