package services;

import common.BaseTestObject;
import domain.locations.Address;
import domain.locations.Location;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IDataGeneratorService;
import services.interfaces.IUserManagementService;
import tools.ConfigurationTools;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class DataGeneratorServiceTest extends BaseTestObject {

    @Autowired
    private IDataGeneratorService dataGeneratorService;

    @Autowired
    private IUserManagementService userManagementService;

    @Test
    public void createUserGroupTest() {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createPasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAccountSecurityProfile(4, 32, 3, 30, 3);
        SecurityProfile securityProfile = dataGeneratorService.createSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        UserGroup userGroup = dataGeneratorService.createUserGroup("Name", "Desc", securityProfile);
        assertEquals(userGroup.getName(), "Name");
        assertEquals(userGroup.getDescription(), "Desc");
        assertEquals(userGroup.getSecurityProfile(), securityProfile);
    }

    @Test
    @Transactional
    public void createAndSaveUserGroupTest() throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createAndSavePasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAndSaveAccountSecurityProfile(4, 32, 3, 30, 3);
        SecurityProfile securityProfile = dataGeneratorService.createAndSaveSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        UserGroup userGroup = dataGeneratorService.createAndSaveUserGroup("Name", "Desc", securityProfile);
        assertNotNull(userGroup.getId());
        UserGroup testUserGroup = userManagementService.getUserGroupById(userGroup.getId());
        assertNotNull(testUserGroup);
    }

    @Test
    @Transactional
    public void createAndSaveSecurityProfilesTest() throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createAndSavePasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        assertNotNull(passwordSecurityProfile.getId());
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAndSaveAccountSecurityProfile(4, 32, 3, 30, 3);
        assertNotNull(accountSecurityProfile.getId());
        SecurityProfile securityProfile = dataGeneratorService.createAndSaveSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        assertNotNull(securityProfile.getId());
    }

    @Test
    public void createIndividualAndUserAccountTest() {
        UserGroup userGroup = dataGeneratorService.createUserGroup("Name", "Desc", null);
        Individual individual = dataGeneratorService.createIndividual("Jan", "Kowalski", "About Me", "Krakow", "Polska");
        assertEquals(individual.getFirstName(), "Jan");
        assertEquals(individual.getLastName(), "Kowalski");
        assertEquals(individual.getDescription(), "About Me");
        assertEquals(individual.getCity(), "Krakow");
        assertEquals(individual.getCountry(), "Polska");
        UserAccount userAccount = dataGeneratorService.createUserAccount("Login", "Password", "Email", individual, userGroup);
        assertEquals(userAccount.getLogin(), "Login");
        assertEquals(userAccount.getPassword(), "Password");
        assertEquals(userAccount.getEmail(), "Email");
        assertEquals(userAccount.getIndividual(), individual);
        assertEquals(userAccount.getUserGroup(), userGroup);
        assertEquals(userAccount.getStatus(), UserAccount.Status.ACTIVE);
    }

    @Test
    @Transactional
    public void createAndSaveIndividualAndUserAccount() throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createAndSavePasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAndSaveAccountSecurityProfile(4, 32, 3, 30, 3);
        SecurityProfile securityProfile = dataGeneratorService.createAndSaveSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        UserGroup userGroup = dataGeneratorService.createAndSaveUserGroup("Name", "Desc", securityProfile);
        Individual individual = dataGeneratorService.createAndSaveIndividual("Jan", "Kowalski", "About Me", "Krakow", "Polska");
        assertNotNull(individual.getId());
        UserAccount userAccount = dataGeneratorService.createAndSaveUserAccount("Login", "Password", "Email", individual, userGroup);
        assertNotNull(userAccount.getId());
    }

    @Test
    public void createAddressAndLocationTest() {
        Address address = dataGeneratorService.createAddress("Street", "City", "PostalCode", "Country");
        assertEquals(address.getStreet(), "Street");
        assertEquals(address.getCity(), "City");
        assertEquals(address.getPostalCode(), "PostalCode");
        assertEquals(address.getCountry(), "Country");
        Location location = dataGeneratorService.createLocation("Name", "Desc", "Url", 10.0, 10.0, false, address, null);
        assertEquals(location.getName(), "Name");
        assertEquals(location.getDescription(), "Desc");
        assertEquals(location.getUrl(), "Url");
        assertEquals(location.getLatitude(), 10.0, 0.001);
        assertEquals(location.getLongitude(), 10.0, 0.001);
        assertEquals(location.isUsersPrivate(), false);
        assertEquals(location.getAddress(), address);
    }

    @Test
    @Transactional
    public void createAndSaveLocation() throws Exception {
        Address address = dataGeneratorService.createAddress("Street", "City", "PostalCode", "Country");
        Location location = dataGeneratorService.createAndSaveLocation("Name", "Desc", "Url", 10.0, 10.0, false, address, null);
        assertNotNull(location.getId());

    }
}
