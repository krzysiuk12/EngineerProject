package common;

import domain.locations.Address;
import domain.locations.Location;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
public abstract class BaseTestObject {

    protected Address createAddress(String city, String country) {
        Address address = new Address();
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    protected Address createAddress(String city, String country, String street, String postalCode) {
        Address address = createAddress(city, country);
        address.setStreet(street);
        address.setPostalCode(postalCode);
        return address;
    }

    protected Location createLocation(String name, double latitude, double longitude, Location.Status status, Address address) {
        Location location = new Location();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setStatus(status);
        location.setAddress(address);
        return location;
    }

    protected Location createLocation(String name, double latitude, double longitude, Location.Status status, Address address, String description, String url, boolean usersPrivate, double rating) {
        Location location = createLocation(name, latitude, longitude, status, address);
        location.setDescription(description);
        location.setUrl(url);
        location.setUsersPrivate(usersPrivate);
        location.setRating(rating);
        return location;
    }

    protected UserAccount createUserAccount(String login, String password, String token, String email, UserAccount.Status status, Individual individual) {
        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(login);
        userAccount.setPassword(password);
        userAccount.setToken(token);
        userAccount.setEmail(email);
        userAccount.setStatus(status);
        userAccount.setIndividual(individual);
        userAccount.setPasswordChangeRequired(false);
        userAccount.setInvalidSignInAttemptsCounter(0);
        userAccount.setLockoutCounter(0);
        return userAccount;
    }

    protected Individual createIndividual(String firstName, String lastName) {
        Individual individual = new Individual();
        individual.setFirstName(firstName);
        individual.setLastName(lastName);
        return individual;
    }

    protected Individual createIndividual(String firstName, String lastName, Date birthDate, String city, String country, String facebookUrl) {
        Individual individual = new Individual();
        individual.setFirstName(firstName);
        individual.setLastName(lastName);
        individual.setCity(city);
        individual.setCountry(country);
        return individual;
    }

    protected Location createMockLocation(double latitude, double longitude) {
        Location location = Mockito.mock(Location.class);
        Mockito.when(location.getLatitude()).thenReturn(latitude);
        Mockito.when(location.getLongitude()).thenReturn(longitude);
        return location;
    }

    protected Location createMockLocation(double latitude, double longitude, String name, Location.Status status) {
        Location location = createMockLocation(latitude, longitude);
        Mockito.when(location.getName()).thenReturn(name);
        Mockito.when(location.getStatus()).thenReturn(status);
        return location;
    }

    protected Address createMockAddress(String city, String country) {
        Address address = Mockito.mock(Address.class);
        Mockito.when(address.getCity()).thenReturn(city);
        Mockito.when(address.getCountry()).thenReturn(country);
        return address;
    }

    protected Address createMockAddress(String city, String country, String street, String postalCode) {
        Address address = createMockAddress(city, country);
        Mockito.when(address.getStreet()).thenReturn(street);
        Mockito.when(address.getPostalCode()).thenReturn(postalCode);
        return address;
    }

    protected UserAccount createMockUserAccount(String login, String password, String token, String email, UserAccount.Status status, Individual individual) {
        UserAccount userAccount = Mockito.mock(UserAccount.class);
        Mockito.when(userAccount.getLogin()).thenReturn(login);
        Mockito.when(userAccount.getPassword()).thenReturn(password);
        Mockito.when(userAccount.getToken()).thenReturn(token);
        Mockito.when(userAccount.getEmail()).thenReturn(email);
        Mockito.when(userAccount.getStatus()).thenReturn(status);
        Mockito.when(userAccount.getIndividual()).thenReturn(individual);
        Mockito.when(userAccount.isPasswordChangeRequired()).thenReturn(false);
        Mockito.when(userAccount.getInvalidSignInAttemptsCounter()).thenReturn(0);
        Mockito.when(userAccount.getLockoutCounter()).thenReturn(0);
        return userAccount;
    }

    protected Individual createMockIndividual(String firstName, String lastName, Date birthDate, String city, String country, String facebookUrl) {
        Individual individual = Mockito.mock(Individual.class);
        Mockito.when(individual.getFirstName()).thenReturn(firstName);
        Mockito.when(individual.getLastName()).thenReturn(lastName);
        Mockito.when(individual.getCity()).thenReturn(city);
        Mockito.when(individual.getCountry()).thenReturn(country);
        return individual;
    }

    protected UserGroup createUserGroup(String name, String desc, SecurityProfile securityProfile) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(desc);
        userGroup.setSecurityProfile(securityProfile);
        return userGroup;
    }

    protected void assertCollectionSize(Collection<?> collection, int size) {
        assertNotNull(collection);
        assertEquals(collection.size(), size);
    }
}
