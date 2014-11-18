package services.interfaces;

import domain.locations.Address;
import domain.locations.Location;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;

import java.util.Date;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface IDataGeneratorService {

    public UserGroup createUserGroup(String name, String desc, SecurityProfile securityProfile) ;

    public UserGroup createAndSaveUserGroup(String name, String desc, SecurityProfile securityProfile) throws Exception;

    public AccountSecurityProfile createAccountSecurityProfile(String name, int minLoginLen, int maxLoginLen, int maxInvalidLoginAttempts, int lockoutDuration, int maxLockoutsBeforeTurningOff);
    public AccountSecurityProfile createAndSaveAccountSecurityProfile(String name, int minLoginLen, int maxLoginLen, int maxInvalidLoginAttempts, int lockoutDuration, int maxLockoutsBeforeTurningOff) throws Exception;

    public PasswordSecurityProfile createPasswordSecurityProfile(String name, int minLen, int maxLen, boolean periodChangeRequired, int maxAge, int infoDays, boolean digitRequired, boolean lowerCaseRequired, boolean upperCaseRequired, boolean specialRequired);
    public PasswordSecurityProfile createAndSavePasswordSecurityProfile(String name, int minLen, int maxLen, boolean periodChangeRequired, int maxAge, int infoDays, boolean digitRequired, boolean lowerCaseRequired, boolean upperCaseRequired, boolean specialRequired) throws Exception;

    public SecurityProfile createSecurityProfile(String name, String desc, boolean isDefault, AccountSecurityProfile accountSecurityProfile, PasswordSecurityProfile passwordSecurityProfile);
    public SecurityProfile createAndSaveSecurityProfile(String name, String desc, boolean isDefault, AccountSecurityProfile accountSecurityProfile, PasswordSecurityProfile passwordSecurityProfile)throws Exception;

    public UserAccount createUserAccount(String login, String password, String email, Individual individual, UserGroup userGroup);
    public UserAccount createAndSaveUserAccount(String login, String password, String email, Individual individual, UserGroup userGroup) throws Exception;

    public Individual createIndividual(String firstName, String middleName, String lastName, Date dateOfBirth, String description, String facebookAccountUrl, String city, String country);
    public Individual createAndSaveIndividual(String firstName, String middleName, String lastName, Date dateOfBirth, String description, String facebookAccountUrl, String city, String country) throws Exception;

    public Address createAddress(String street, String city, String postalCode, String country);
    public Address createAndSaveAddress(String street, String city, String postalCode, String country) throws Exception;

    public Location createLocation(String name, String desc, String url, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy);
    public Location createAndSaveLocation(String name, String desc, String url, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) throws Exception;

}
