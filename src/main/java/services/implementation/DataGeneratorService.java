package services.implementation;

import domain.locations.Address;
import domain.locations.Location;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.*;

import java.util.Date;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
public class DataGeneratorService implements IDataGeneratorService {

    private ICodeGeneratorService codeGeneratorService;
    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;
    private ISecurityProfileManagementService securityProfileManagementService;

    @Autowired
    public DataGeneratorService(ICodeGeneratorService codeGeneratorService, IUserManagementService userManagementService, ILocationManagementService locationManagementService, ISecurityProfileManagementService securityProfileManagementService) {
        this.codeGeneratorService = codeGeneratorService;
        this.userManagementService = userManagementService;
        this.locationManagementService = locationManagementService;
        this.securityProfileManagementService = securityProfileManagementService;
    }

    @Override
    public UserGroup createUserGroup(String name, String desc, SecurityProfile securityProfile) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(desc);
        userGroup.setSecurityProfile(securityProfile);
        return userGroup;
    }

    @Override
    @Transactional
    public UserGroup createAndSaveUserGroup(String name, String desc, SecurityProfile securityProfile) throws Exception {
        UserGroup userGroup = createUserGroup(name, desc, securityProfile);
        userManagementService.saveUserGroup(userGroup);
        return userGroup;
    }

    @Override
    public AccountSecurityProfile createAccountSecurityProfile(int minLoginLen, int maxLoginLen, int maxInvalidLoginAttempts, int lockoutDuration, int maxLockoutsBeforeTurningOff) {
        AccountSecurityProfile accountSecurityProfile = new AccountSecurityProfile();
        accountSecurityProfile.setMinimumLoginLength(minLoginLen);
        accountSecurityProfile.setMaximumLoginLength(maxLoginLen);
        accountSecurityProfile.setMaximumInvalidLogInAttempts(maxInvalidLoginAttempts);
        accountSecurityProfile.setAccountLockOutDurationInMinutes(lockoutDuration);
        accountSecurityProfile.setAccountImmediatelyTurnedOff(maxLockoutsBeforeTurningOff == 0);
        accountSecurityProfile.setMaximumLockoutsBeforeTurningOff(maxLockoutsBeforeTurningOff);
        return accountSecurityProfile;
    }

    @Override
    @Transactional
    public AccountSecurityProfile createAndSaveAccountSecurityProfile(int minLoginLen, int maxLoginLen, int maxInvalidLoginAttempts, int lockoutDuration, int maxLockoutsBeforeTurningOff) throws Exception {
        AccountSecurityProfile accountSecurityProfile = createAccountSecurityProfile(minLoginLen, maxLoginLen, maxInvalidLoginAttempts, lockoutDuration, maxLockoutsBeforeTurningOff);
        securityProfileManagementService.saveAccountSecurityProfile(accountSecurityProfile);
        return accountSecurityProfile;
    }

    @Override
    public PasswordSecurityProfile createPasswordSecurityProfile(int minLen, int maxLen, boolean periodChangeRequired, int maxAge, int infoDays, boolean digitRequired, boolean lowerCaseRequired, boolean upperCaseRequired, boolean specialRequired) {
        PasswordSecurityProfile passwordSecurityProfile = new PasswordSecurityProfile();
        passwordSecurityProfile.setMinimumLength(minLen);
        passwordSecurityProfile.setMaximumLength(maxLen);
        passwordSecurityProfile.setPeriodPasswordChangeRequired(periodChangeRequired);
        passwordSecurityProfile.setMaximumAgeInDays(maxAge);
        passwordSecurityProfile.setExpirationInfoInDays(infoDays);
        passwordSecurityProfile.setDigitRequired(digitRequired);
        passwordSecurityProfile.setLowerCaseLetterRequired(lowerCaseRequired);
        passwordSecurityProfile.setUpperCaseLetterRequired(upperCaseRequired);
        passwordSecurityProfile.setSpecialCharacterRequired(specialRequired);
        return passwordSecurityProfile;
    }

    @Override
    @Transactional
    public PasswordSecurityProfile createAndSavePasswordSecurityProfile(int minLen, int maxLen, boolean periodChangeRequired, int maxAge, int infoDays, boolean digitRequired, boolean lowerCaseRequired, boolean upperCaseRequired, boolean specialRequired) throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = createPasswordSecurityProfile(minLen, maxLen, periodChangeRequired, maxAge, infoDays, digitRequired, lowerCaseRequired, upperCaseRequired, specialRequired);
        securityProfileManagementService.savePasswordSecurityProfile(passwordSecurityProfile);
        return passwordSecurityProfile;
    }

    @Override
    public SecurityProfile createSecurityProfile(String name, String desc, boolean isDefault, AccountSecurityProfile accountSecurityProfile, PasswordSecurityProfile passwordSecurityProfile) {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setName(name);
        securityProfile.setDescription(desc);
        securityProfile.setDefaultProfile(isDefault);
        securityProfile.setStatus(SecurityProfile.Status.ACTIVE);
        securityProfile.setPasswordSecurityProfile(passwordSecurityProfile);
        securityProfile.setPasswordSecurityProfileTurnedOn(passwordSecurityProfile != null);
        securityProfile.setAccountSecurityProfile(accountSecurityProfile);
        securityProfile.setAccountSecurityProfileTurnedOn(accountSecurityProfile != null);
        return securityProfile;
    }

    @Override
    @Transactional
    public SecurityProfile createAndSaveSecurityProfile(String name, String desc, boolean isDefault, AccountSecurityProfile accountSecurityProfile, PasswordSecurityProfile passwordSecurityProfile) throws Exception {
        SecurityProfile securityProfile = createSecurityProfile(name, desc, isDefault, accountSecurityProfile, passwordSecurityProfile);
        securityProfileManagementService.saveSecurityProfile(securityProfile);
        return securityProfile;
    }

    @Override
    public UserAccount createUserAccount(String login, String password, String email, Individual individual, UserGroup userGroup) {
        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(login);
        userAccount.setPassword(password);
        userAccount.setEmail(email);
        userAccount.setToken(codeGeneratorService.generateSessionToken());
        userAccount.setStatus(UserAccount.Status.ACTIVE);
        userAccount.setPasswordChangeRequired(false);
        userAccount.setInvalidSignInAttemptsCounter(0);
        userAccount.setLockoutCounter(0);
        userAccount.setIndividual(individual);
        userAccount.setUserGroup(userGroup);
        return userAccount;
    }

    @Override
    @Transactional
    public UserAccount createAndSaveUserAccount(String login, String password, String email, Individual individual, UserGroup userGroup) throws Exception {
        UserAccount userAccount = createUserAccount(login, password, email, individual, userGroup);
        userManagementService.saveUserAccount(userAccount);
        return userAccount;
    }

    @Override
    public Individual createIndividual(String firstName, String middleName, String lastName, Date dateOfBirth, String description, String facebookAccountUrl, String city, String country) {
        Individual individual = new Individual();
        individual.setFirstName(firstName);
        individual.setLastName(lastName);
        individual.setDescription(description);
        individual.setCity(city);
        individual.setCountry(country);
        return individual;
    }

    @Override
    @Transactional
    public Individual createAndSaveIndividual(String firstName, String middleName, String lastName, Date dateOfBirth, String description, String facebookAccountUrl, String city, String country) throws Exception {
        Individual individual = createIndividual(firstName, middleName, lastName, dateOfBirth, description, facebookAccountUrl, city, country);
        userManagementService.saveIndividual(individual);
        return individual;
    }

    @Override
    public Address createAddress(String street, String city, String postalCode, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        return address;
    }

    @Override
    public Location createLocation(String name, String desc, String url, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(desc);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setStatus(Location.Status.AVAILABLE);
        location.setUsersPrivate(usersPrivate);
        location.setAddress(address);
        location.setUrl(url);
        location.setRating(0.0);
        location.updateInformation(createdBy);
        return location;
    }

    @Override
    @Transactional
    public Location createAndSaveLocation(String name, String desc, String url, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) throws Exception {
        Location location = createLocation(name, desc, url, latitude, longitude, usersPrivate, address, createdBy);
        locationManagementService.saveLocation(location, createdBy);
        return location;
    }


}
