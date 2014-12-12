package services;

import common.BaseTestObject;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IDataGeneratorService;
import services.interfaces.ISecurityProfileManagementService;
import services.interfaces.IUserManagementService;
import tools.ConfigurationTools;

import javax.transaction.Transactional;
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

    @Autowired
    private IDataGeneratorService dataGeneratorService;

    @Autowired
    private ISecurityProfileManagementService securityProfileManagementService;

    @Test
    @Transactional
    public void saveUserAccountTest() throws Exception {
        String login = "JanKowalskiLogin";
        String email = "jankowalski@email.com";
        List<UserAccount> accounts = userManagementService.getAllUserAccounts();
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount(login, "SavePassword", "SaveToken", email, UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        List<UserAccount> accounts2 = userManagementService.getAllUserAccounts();
        assertCollectionSize(accounts2, accounts.size() + 1);

        saveUserAccountExpectedError(createUserAccount(null, "Password", "Token", "Email", UserAccount.Status.ACTIVE, individual), ErrorMessages.INVALID_USER_LOGIN);
        saveUserAccountExpectedError(createUserAccount("Login", null, "Token", "Email", UserAccount.Status.ACTIVE, individual), ErrorMessages.INVALID_USER_PASSWORD);
        saveUserAccountExpectedError(createUserAccount("Login", "Password", "Token", "Email", null, individual), ErrorMessages.INVALID_USER_STATUS);
        saveUserAccountExpectedError(createUserAccount("Login", "Password", "Token", null, UserAccount.Status.ACTIVE, individual), ErrorMessages.INVALID_USER_EMAIL);
    }

    @Test
    @Transactional
    public void saveIndividualTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        assertNotNull(individual);
        assertNotNull(individual.getId());

        saveIndividualExpectedError(createIndividual(null, "Kowalski"), ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        saveIndividualExpectedError(createIndividual("Jan", null), ErrorMessages.INVALID_INDIVIDUAL_LAST_NAME);
        saveIndividualExpectedError(createIndividual("jAn", "Kowalski"), ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        saveIndividualExpectedError(createIndividual("Jan", "kowalski"), ErrorMessages.INVALID_INDIVIDUAL_LAST_NAME);
    }

    @Test
    @Transactional
    public void saveAndGetUserGroupTest() throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createPasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        securityProfileManagementService.savePasswordSecurityProfile(passwordSecurityProfile);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAccountSecurityProfile(4, 32, 3, 30, 3);
        securityProfileManagementService.saveAccountSecurityProfile(accountSecurityProfile);
        SecurityProfile securityProfile = dataGeneratorService.createSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        securityProfileManagementService.saveSecurityProfile(securityProfile);

        SecurityProfile defaultSecurityProfile = securityProfileManagementService.getDefaultSecurityProfile();

        UserGroup userGroup = dataGeneratorService.createUserGroup("Group1", "Description group 1", defaultSecurityProfile);
        userManagementService.saveUserGroup(userGroup);

        UserGroup userGroup2 = dataGeneratorService.createUserGroup("Group2", "Description group 2", defaultSecurityProfile);
        userManagementService.saveUserGroup(userGroup2);

        UserGroup byIdUserGroup1 = userManagementService.getUserGroupById(userGroup.getId());
        assertNotNull(byIdUserGroup1);
        assertEquals(byIdUserGroup1.getId(), userGroup.getId());
        UserGroup byIdUserGroup2 = userManagementService.getUserGroupById(userGroup2.getId());
        assertNotNull(byIdUserGroup2);
        assertEquals(byIdUserGroup2.getId(), userGroup2.getId());
        UserGroup byNameUserGroup1 = userManagementService.getUserGroupByName(userGroup.getName());
        assertNotNull(byNameUserGroup1);
        assertEquals(byNameUserGroup1.getName(), userGroup.getName());
        UserGroup byNameUserGroup2 = userManagementService.getUserGroupByName(userGroup2.getName());
        assertNotNull(byNameUserGroup2);
        assertEquals(byNameUserGroup2.getName(), userGroup2.getName());
    }

    @Test
    @Transactional
    public void addUserAccountTest() throws Exception {
        String login = "newAccountLogin";
        userManagementService.addUserAccount(login, "Password", "jankowalski@gmail.com", "Jan", "Kowalski");
        UserAccount account = userManagementService.getUserAccountByLogin(login);
        assertNotNull(account);
        assertEquals(account.getLogin(), login);

        addNewUserAccountExpectedError(login, "Password", "jankowalski2@gmail.com", "Jan", "Kowalski", ErrorMessages.INVALID_USER_LOGIN_EXISTS_IN_SYSTEM);
        addNewUserAccountExpectedError("anotherLogin", "Password", "jankowalski@gmail.com", "Jan", "Kowalski", ErrorMessages.INVALID_USER_EMAIL_EXISTS_IN_SYSTEM);
    }

    @Test
    @Transactional
    public void updateUserAccountTest() throws Exception {
        String email = "newemail@email.com";
        String firstName = "Artur";
        String lastName = "Kowal";
        String city = "Tokio";
        String country = "Japonia";
        String description = "Test Description New";

        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "Token", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        userManagementService.updateUserAccount(email, firstName, lastName, city, country, description, account.getToken());

        UserAccount testAccount = userManagementService.getUserAccountByIdAllData(account.getId());
        assertNotNull(testAccount);
        assertEquals(testAccount.getEmail(), email);
        assertEquals(testAccount.getIndividual().getFirstName(), firstName);
        assertEquals(testAccount.getIndividual().getLastName(), lastName);
        assertEquals(testAccount.getIndividual().getCity(), city);
        assertEquals(testAccount.getIndividual().getCountry(), country);
    }

    @Test
    @Transactional
    public void userAccountGettersTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "Token", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        UserAccount byIdUserAccount = userManagementService.getUserAccountById(account.getId());
        assertNotNull(byIdUserAccount);
        assertEquals(account.getId(), byIdUserAccount.getId());

        UserAccount byIdAllDataUserAccount = userManagementService.getUserAccountByIdAllData(account.getId());
        assertNotNull(byIdAllDataUserAccount);
        assertNotNull(byIdAllDataUserAccount.getIndividual());
        assertEquals(account.getId(), byIdAllDataUserAccount.getId());

        UserAccount byLoginUserAccount = userManagementService.getUserAccountByLogin(account.getLogin());
        assertNotNull(byLoginUserAccount);
        assertEquals(byLoginUserAccount.getLogin(), account.getLogin());

        UserAccount byTokenUserAccount = userManagementService.getUserAccountByToken(account.getToken());
        assertNotNull(byTokenUserAccount);
        assertEquals(byTokenUserAccount.getToken(), byLoginUserAccount.getToken());
    }

    @Test
    @Transactional
    public void authenticationUserAccountTest() throws Exception {
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
    }

    @Test
    @Transactional
    public void changeUserAccountPasswordTest() throws Exception{
        String currentPassword = "CurrentPassword";
        String newPassword = "NewPassword";

        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("SimpleLogin1", currentPassword, "SimpleToken1", "SimpleEmail1", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        userManagementService.changeUserAccountPassword(currentPassword, newPassword, account.getToken());

        UserAccount testUserAccount = userManagementService.getUserAccountById(account.getId());
        assertEquals(newPassword, testUserAccount.getPassword());
    }

    private void assertUserAccounts(UserAccount baseLocation, UserAccount testedLocation) throws Exception {
        assertNotNull(testedLocation);
        assertEquals(baseLocation.getId(), testedLocation.getId());
        assertEquals(baseLocation.getLogin(), testedLocation.getLogin());
        assertEquals(baseLocation.getPassword(), testedLocation.getPassword());
        assertEquals(baseLocation.getToken(), testedLocation.getToken());
        assertEquals(baseLocation.getEmail(), testedLocation.getEmail());
    }

    private void saveUserAccountExpectedError(UserAccount account, ErrorMessages message) throws Exception {
        try {
            userManagementService.saveUserAccount(account);
        } catch (FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }

    private void saveIndividualExpectedError(Individual individual, ErrorMessages message) throws Exception {
        try {
            userManagementService.saveIndividual(individual);
        } catch (FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }

    private void addNewUserAccountExpectedError(String login, String password, String email, String firstName, String lastName, ErrorMessages message) throws Exception {
        try {
            userManagementService.addUserAccount(login, password, email, firstName, lastName);
        } catch (FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }
}
