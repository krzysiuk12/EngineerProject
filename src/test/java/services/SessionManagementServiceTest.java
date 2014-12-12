package services;

import common.BaseTestObject;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ISessionManagementService;
import services.interfaces.IUserManagementService;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class SessionManagementServiceTest extends BaseTestObject {

    @Autowired
    private ISessionManagementService sessionManagementService;

    @Autowired
    private IUserManagementService userManagementService;

    @Test
    @Transactional
    public void testLoginUser() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        String token = sessionManagementService.loginUser(account.getLogin(), account.getPassword(), "192.168.0.0", "1234567890");
        assertNotNull(token);
        assertEquals(token.length(), 20);

        loginUserExpectedError("NonExistingLogin", account.getPassword(), "192.168.0.0", "1234567890", ErrorMessages.INVALID_LOGIN);
        loginUserExpectedError(account.getLogin(), "AnotherPassword", "192.168.0.0", "1234567890", ErrorMessages.INVALID_PASSWORD);

        Individual lockedOutIndividual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(lockedOutIndividual);
        UserAccount lockedOutUserAccount = createUserAccount("LoginUserLogin2", "LoginUserPassword", "LoginUserToken", "LoginUserEmail2", UserAccount.Status.LOCKED_OUT, lockedOutIndividual);
        userManagementService.saveUserAccount(lockedOutUserAccount);
        loginUserExpectedError(lockedOutUserAccount.getLogin(), lockedOutUserAccount.getPassword(), "192.168.0.0", "1234567890", ErrorMessages.USER_ACCOUNT_NOT_ACTIVE);
    }

    @Test
    @Transactional
    public void testAccountLockingOut() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        loginUserExpectedError(account.getLogin(), "InvalidPassword", "192.168.0.0", "1234567890", ErrorMessages.INVALID_PASSWORD);
        loginUserExpectedError(account.getLogin(), "InvalidPassword", "192.168.0.0", "1234567890", ErrorMessages.INVALID_PASSWORD);
        loginUserExpectedError(account.getLogin(), "InvalidPassword", "192.168.0.0", "1234567890", ErrorMessages.INVALID_PASSWORD);
        UserAccount userAccount = userManagementService.getUserAccountById(account.getId());
        //assertEquals(userAccount.getStatus(), UserAccount.Status.LOCKED_OUT);
    }

    @Test
    @Transactional
    public void testLogoutUser() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        String token = sessionManagementService.loginUser(account.getLogin(), account.getPassword(), "192.168.0.0", "1234567890");
        assertNotNull(token);
        assertEquals(token.length(), 20);
        sessionManagementService.logoutUser(token, "192.168.0.0", "1234567890");
        UserAccount logoutUser = userManagementService.getUserAccountById(account.getId());
        assertNotNull(logoutUser);
        assertNull(logoutUser.getToken());


    }

    private void loginUserExpectedError(String login, String password, String ipAddress, String sessionId, ErrorMessages message) throws Exception {
        try {
            sessionManagementService.loginUser(login, password, ipAddress, sessionId);
        } catch(FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }
}
