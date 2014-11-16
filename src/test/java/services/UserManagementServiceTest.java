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
import services.interfaces.IUserManagementService;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class UserManagementServiceTest extends BaseTestObject {

    @Autowired
    private IUserManagementService userManagementService;

    @Test
    public void saveUserAccountTest() {
        try {
            List<UserAccount> accounts = userManagementService.getAllUserAccounts();
            Individual individual = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual);
            UserAccount account = createUserAccount("SaveLogin", "SavePassword", "SaveToken", "SaveEmail", UserAccount.Status.ACTIVE, individual);
            userManagementService.saveUserAccount(account);
            List<UserAccount> accounts2 = userManagementService.getAllUserAccounts();
            assertCollectionSize(accounts2, accounts.size() + 1);
        } catch(Exception ex) {
            fail("Failed saveUserAccountTest.");
        }
    }

    @Test
    public void getUserAccountTest() {
        try {
            List<UserAccount> accounts = userManagementService.getAllUserAccounts();

            Individual individual = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual);
            UserAccount account = createUserAccount("GetLogin1", "GetPassword1", "GetToken1", "GetEmail1", UserAccount.Status.ACTIVE, individual);
            userManagementService.saveUserAccount(account);

            Individual individual2 = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual2);
            UserAccount account2 = createUserAccount("GetLogin2", "GetPassword2", "GetToken2", "GetEmail2", UserAccount.Status.ACTIVE, individual2);
            userManagementService.saveUserAccount(account2);

            List<UserAccount> accounts2 = userManagementService.getAllUserAccounts();
            assertCollectionSize(accounts2, accounts.size() + 2);

            UserAccount userAccountByLogin1 = userManagementService.getUserAccountByLogin(account.getLogin());
            UserAccount userAccountByLogin2 = userManagementService.getUserAccountByLogin(account2.getLogin());
            assertUserAccounts(account, userAccountByLogin1);
            assertUserAccounts(account2, userAccountByLogin2);

            UserAccount userAccountByToken1 = userManagementService.getUserAccountByToken(account.getToken());
            UserAccount userAccountByToken2 = userManagementService.getUserAccountByToken(account2.getToken());
            assertUserAccounts(account, userAccountByToken1);
            assertUserAccounts(account2, userAccountByToken2);

            UserAccount userAccountById1 = userManagementService.getUserAccountById(account.getId());
            UserAccount userAccountById2 = userManagementService.getUserAccountById(account2.getId());
            assertUserAccounts(account, userAccountById1);
            assertUserAccounts(account2, userAccountById2);
        } catch(Exception ex) {
            fail("Failed getUserAccountTest.");
        }
    }

    @Test
    public void authorizationUserAccountTest() {
        try {
            List<UserAccount> accounts = userManagementService.getAllUserAccounts();

            Individual individual = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual);
            UserAccount account = createUserAccount("SimpleLogin1", "SimplePassword1", "SimpleToken1", "SimpleEmail1", UserAccount.Status.ACTIVE, individual);
            userManagementService.saveUserAccount(account);

            Individual individual2 = createIndividual("Jan", "Kowalski");
            userManagementService.saveIndividual(individual2);
            UserAccount account2 = createUserAccount("admin", "admin", "admin", "adminEmail", UserAccount.Status.ACTIVE, individual2);
            userManagementService.saveUserAccount(account2);

            assertTrue(userManagementService.authenticateUserAccountByToken(account.getToken()));
            assertTrue(userManagementService.authenticateAdminAccountByToken(account2.getToken()));
            assertTrue(userManagementService.authenticateUserAccountByToken(account2.getToken()));
            assertFalse(userManagementService.authenticateAdminAccountByToken(account.getToken()));
        } catch(Exception ex) {
            fail("Failed getUserAccountTest.");
        }
    }

    private void assertUserAccounts(UserAccount baseLocation, UserAccount testedLocation) throws Exception {
        assertNotNull(testedLocation);
        assertEquals(baseLocation.getId(), testedLocation.getId());
        assertEquals(baseLocation.getLogin(), testedLocation.getLogin());
        assertEquals(baseLocation.getPassword(), testedLocation.getPassword());
        assertEquals(baseLocation.getToken(), testedLocation.getToken());
        assertEquals(baseLocation.getEmail(), testedLocation.getEmail());
    }
}
