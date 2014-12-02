package controllers.rest;

import annotations.AdminAuthorized;
import annotations.NotAuthorized;
import domain.useraccounts.UserAccount;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.users.ChangePasswordSerializer;
import jsonserializers.users.UserAccountSerializer;
import jsonserializers.users.UserRegistrationSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Controller
@RequestMapping(value = "/users")
public class UserAccountRestController {

    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;

    @Autowired
    public UserAccountRestController(IUserManagementService userManagementService, ILocationManagementService locationManagementService) {
        this.userManagementService = userManagementService;
        this.locationManagementService = locationManagementService;
    }

    //<editor-fold desc="Add New UserAccount">
    @NotAuthorized
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer addNewUserAccount(@RequestBody UserRegistrationSerializer data) throws Exception {
        userManagementService.addUserAccount(data.getLogin(), data.getPassword(), data.getEmail(), data.getFirstName(), data.getLastName());
        return new ResponseSerializer();
    }
    //</editor-fold>

    //<editor-fold desc="Get UserAccount By Id">
    @AdminAuthorized
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getUserAccountById(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        UserAccount userAccount =  userManagementService.getUserAccountById(userId);
        userAccount.setIndividual(null);
        return new ResponseSerializer(userAccount);
    }

    @AdminAuthorized
    @RequestMapping(value = "/{userId}/all", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getUserAccountByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("userId") Long userId) throws Exception {
        return new ResponseSerializer(userManagementService.getUserAccountByIdAllData(userId));
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getMyUserAccountData(@RequestHeader(value = "authorization") String token) throws Exception {
        UserAccount userAccount =  userManagementService.getUserAccountByToken(token);
        userAccount.setIndividual(null);
        return new ResponseSerializer(userAccount);
    }

    @RequestMapping(value = "/my/all", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<UserAccount> getMyUserAccountAllData(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(userManagementService.getUserAccountByIdAllData(userManagementService.getUserAccountByToken(token).getId()));
    }
    //</editor-fold>

    //<editor-fold desc="Get UserAccounts">
    @AdminAuthorized
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<UserAccount>> getAllUserAccounts(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(userManagementService.getAllUserAccounts());
    }
    //</editor-fold>

    //<editor-fold desc="Change UserAccount Password">
    @RequestMapping(value = "/my/password", method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer<UserAccount> changeUserAccountPassword(@RequestHeader(value = "authorization") String token, @RequestBody ChangePasswordSerializer changePasswordSerializer) throws Exception {
        userManagementService.changeUserAccountPassword(changePasswordSerializer.getCurrentPassword(), changePasswordSerializer.getNewPassword(), token);
        return new ResponseSerializer();
    }
    //</editor-fold>

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer updateUserAccount(@RequestHeader(value = "authorization") String token, @RequestBody UserAccountSerializer uas) throws Exception {
        userManagementService.updateUserAccount(uas.getEmail(), uas.getFirstName(), uas.getLastName(), uas.getCity(), uas.getCountry(), uas.getDescription(), token);
        return new ResponseSerializer();
    }
}
