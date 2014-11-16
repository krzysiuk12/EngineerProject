package services;

import common.BaseTestObject;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ISessionManagementService;
import services.interfaces.IUserManagementService;

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
    public void testLoginUser() {
        try {
            Individual individual = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual);
            UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
            userManagementService.saveUserAccount(account);
            String token = sessionManagementService.loginUser(account.getLogin(), account.getPassword(), "192.168.0.0", "1234567890");
            assertNotNull(token);
            assertEquals(token.length(), 20);
        } catch(Exception ex) {
            fail("Failed testLoginUser.");
        }
    }
}
