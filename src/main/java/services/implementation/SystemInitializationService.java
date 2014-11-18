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
import services.interfaces.ICodeGeneratorService;

import java.util.Date;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
public class SystemInitializationService {

    @Autowired
    private ICodeGeneratorService codeGeneratorService;

    private UserGroup createUserGroup(String name, String desc, SecurityProfile securityProfile) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(desc);
        userGroup.setSecurityProfile(securityProfile);
        return userGroup;
    }

    private AccountSecurityProfile createAccountSecurityProfile(String name, int minLoginLen, int maxLoginLen, int maxInvalidLoginAttemtpts, int lockoutDuration, int maxLockoutsBeforeTurningOff) {
        AccountSecurityProfile accountSecurityProfile = new AccountSecurityProfile();
        accountSecurityProfile.setName(name);
        accountSecurityProfile.setMinimumLoginLength(minLoginLen);
        accountSecurityProfile.setMaximumLoginLength(maxLoginLen);
        accountSecurityProfile.setMaximumInvalidLogInAttempts(maxInvalidLoginAttemtpts);
        accountSecurityProfile.setAccountLockOutDurationInMinutes(lockoutDuration);
        accountSecurityProfile.setAccountImmediatelyTurnedOff(maxLockoutsBeforeTurningOff == 0);
        accountSecurityProfile.setMaximumLockoutsBeforeTurningOff(maxLockoutsBeforeTurningOff);
        return accountSecurityProfile;
    }

    private PasswordSecurityProfile createPasswordSecurityProfile(String name, int minLen, int maxLen, boolean periodChangeRequired, int maxAge, int infoDays, boolean digitRequired, boolean lowerCaseRequired, boolean upperCaseRequired, boolean specialRequired) {
        PasswordSecurityProfile passwordSecurityProfile = new PasswordSecurityProfile();
        passwordSecurityProfile.setName(name);
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

    private SecurityProfile createSecurityProfile(String name, String desc, boolean isDedault, AccountSecurityProfile accountSecurityProfile, PasswordSecurityProfile passwordSecurityProfile) {
        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setName(name);
        securityProfile.setDescription(desc);
        securityProfile.setDefaultProfile(isDedault);
        securityProfile.setStatus(SecurityProfile.Status.ACTIVE);
        securityProfile.setPasswordSecurityProfile(passwordSecurityProfile);
        securityProfile.setPasswordSecurityProfileTurnedOn(passwordSecurityProfile != null);
        securityProfile.setAccountSecurityProfile(accountSecurityProfile);
        securityProfile.setAccountSecurityProfileTurnedOn(accountSecurityProfile != null);
        return securityProfile;
    }

    private UserAccount createUserAccount(String login, String password, String email, Individual individual, UserGroup userGroup) {
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

    private Individual createIndividual(String firstName, String middleName, String lastName, Date dateOfBirth, String description, String facebookAccountUrl, String city, String country) {
        Individual individual = new Individual();
        individual.setFirstName(firstName);
        individual.setMiddleName(middleName);
        individual.setLastName(lastName);
        individual.setDateOfBirth(dateOfBirth);
        individual.setDescription(description);
        individual.setFacebookAccountUrl(facebookAccountUrl);
        individual.setCity(city);
        individual.setCountry(country);
        return individual;
    }

    protected Address createAddress(String street, String city, String postalCode, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        return address;
    }

    private Location createLocation(String name, String desc, String url, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) {
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
        return location;
    }

}
