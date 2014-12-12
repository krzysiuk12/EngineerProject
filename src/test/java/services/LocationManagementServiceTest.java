package services;

import common.BaseTestObject;
import domain.locations.Comment;
import domain.locations.Location;
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
import org.springframework.transaction.annotation.Transactional;
import services.implementation.LocationManagementService;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class LocationManagementServiceTest extends BaseTestObject {

    @Autowired
    private ILocationManagementService locationManagementService;

    @Autowired
    private IUserManagementService userManagementService;

    @Test
    @Transactional
    public void saveOrUpdateLocationTest() throws Exception {
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, null);
        List<Location> locations = locationManagementService.getAllLocations();
        assertCollectionSize(locations, 1);

        Location location2 = createLocation("SaveTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location2, null);
        locations = locationManagementService.getAllLocations();
        assertCollectionSize(locations, 2);

        Location location3 = createLocation("SaveTest3", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location3, null);
        locations = locationManagementService.getAllLocations();
        assertCollectionSize(locations, 3);

        saveLocationExpectedError(createLocation(null, 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), ErrorMessages.INVALID_LOCATION_NAME);
        saveLocationExpectedError(createLocation("LocationName", 10.0, 10.0, null, createAddress("Krakow", "Polska")), ErrorMessages.INVALID_LOCATION_STATUS);
        saveLocationExpectedError(createLocation("LocationName", 10.0, 10.0, Location.Status.AVAILABLE, null), ErrorMessages.INVALID_LOCATION_ADDRESS);
        saveLocationExpectedError(createLocation("LocationName", 10.0, 10.0, Location.Status.AVAILABLE, createAddress(null, "Polska")), ErrorMessages.INVALID_ADDRESS_CITY);
        saveLocationExpectedError(createLocation("LocationName", 90.01, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", null)), ErrorMessages.INVALID_LOCATION_LATITUDE);
        saveLocationExpectedError(createLocation("LocationName", -90.01, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), ErrorMessages.INVALID_LOCATION_LATITUDE);
        saveLocationExpectedError(createLocation("LocationName", 10.0, 180.01, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), ErrorMessages.INVALID_LOCATION_LONGITUDE);
        saveLocationExpectedError(createLocation("LocationName", 10.0, -180.01, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), ErrorMessages.INVALID_LOCATION_LONGITUDE);
    }

    @Test
    @Transactional
    public void addCommentTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, account);
        Comment comment = new Comment(account, location, Comment.Rating.BAD, "Bad Comment", new Date());
        locationManagementService.saveComment(comment);

        saveCommentExpectedError(new Comment(null, location, Comment.Rating.BAD, "Bad Comment", new Date()), ErrorMessages.INVALID_USER_ACCOUNT);
        saveCommentExpectedError(new Comment(account, null, Comment.Rating.BAD, "Bad Comment", new Date()), ErrorMessages.INVALID_LOCATION);
        saveCommentExpectedError(new Comment(account, location, null, "Bad Comment", new Date()), ErrorMessages.INVALID_RATING);
        saveCommentExpectedError(new Comment(account, location, Comment.Rating.BAD, "Bad Comment", null), ErrorMessages.INVALID_DATE);

    }

    @Test
    @Transactional
    public void testUpdateLocation() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, account);

        String name = "Updated Name";
        String description = "Updated Description";
        String url = "Updated url";
        Location.Status status = Location.Status.AVAILABLE;
        double latitude = 0.0;
        double longitude = 0.0;
        String street = "Updated Street";
        String postalCode = "42-543";
        String city = "Updated City";
        String country = "Updated Country";
        locationManagementService.updateLocation(location.getId(), name, description, url, status, longitude, latitude, street, postalCode, city, country, account.getToken());

        Location updatedLocation = locationManagementService.getLocationById(location.getId());
        assertNotNull(updatedLocation);
        assertEquals(updatedLocation.getName(), name);
        assertEquals(updatedLocation.getDescription(), description);
        assertEquals(updatedLocation.getUrl(), url);
        assertEquals(updatedLocation.getStatus(), status);
        assertEquals(updatedLocation.getLatitude(), latitude, 0.001);
        assertEquals(updatedLocation.getLongitude(), longitude, 0.001);
        assertEquals(updatedLocation.getAddress().getStreet(), street);
        assertEquals(updatedLocation.getAddress().getCity(), city);
        assertEquals(updatedLocation.getAddress().getCountry(), country);
        assertEquals(updatedLocation.getAddress().getPostalCode(), postalCode);
    }

    @Test
    @Transactional
    public void addNewLocation() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.addNewLocation(location.getName(), location.getDescription(), location.getUrl(), location.getStatus(), location.getLongitude(), location.getLatitude(), location.getAddress().getStreet(), location.getAddress().getPostalCode(), location.getAddress().getCity(), location.getAddress().getCountry(), account.getToken());
        List<Location> locations = locationManagementService.getAllLocations();
        assertNotNull(locations);
        assertCollectionSize(locations, 1);
        assertFalse(locations.get(0).isUsersPrivate());
        assertEquals(locations.get(0).getName(), location.getName());
        assertEquals(locations.get(0).getLatitude(), location.getLatitude(), 0.01);
        assertEquals(locations.get(0).getLongitude(), location.getLongitude(), 0.01);
    }

    @Test
    @Transactional
    public void addNewPrivateLocation() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.addNewPrivateLocation(location.getName(), location.getDescription(), location.getUrl(), location.getStatus(), location.getLongitude(), location.getLatitude(), location.getAddress().getStreet(), location.getAddress().getPostalCode(), location.getAddress().getCity(), location.getAddress().getCountry(), account.getToken());
        List<Location> locations = locationManagementService.getAllUsersPrivateLocations(account.getId());
        assertNotNull(locations);
        assertCollectionSize(locations, 1);
        assertTrue(locations.get(0).isUsersPrivate());
        assertEquals(locations.get(0).getName(), location.getName());
        assertEquals(locations.get(0).getLatitude(), location.getLatitude(), 0.01);
        assertEquals(locations.get(0).getLongitude(), location.getLongitude(), 0.01);
    }

    @Test
    @Transactional
    public void addNewComment() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken123", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, account);

        locationManagementService.addNewComment(location.getId(), Comment.Rating.EXCELLENT, "Some Comment", account.getToken());
/*        Location testLocation = locationManagementService.getLocationByIdAllData(location.getId());
        assertNotNull(testLocation);
        assertNotNull(testLocation.getComments());
        assertCollectionSize(testLocation.getComments(), 1);*/
    }

    @Test
    @Transactional
    public void getLocationByIdTest() throws Exception {
        Location location = createLocation("GetLocationByIdTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, null);
        Location location2 = createLocation("GetLocationByIdTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location2, null);
        Location resultLocation = locationManagementService.getLocationById(location.getId());
        compareLocations(location, resultLocation);
        Location resultLocationAllData = locationManagementService.getLocationByIdAllData(location.getId());
        compareLocations(location, resultLocationAllData);
        Location resultLocation2 = locationManagementService.getLocationById(location2.getId());
        compareLocations(location2, resultLocation2);
        Location resultLocationAllData2 = locationManagementService.getLocationByIdAllData(location2.getId());
        compareLocations(location2, resultLocationAllData2);
    }

    @Test
    @Transactional
    public void getMyLocationByIdTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        locationManagementService.addNewPrivateLocation("Name", "Description", null, Location.Status.AVAILABLE, 10.0, 10.0, null, null, "City", "Country", account.getToken());

        List<Location> locations = locationManagementService.getAllUsersPrivateLocations(account.getId());
        assertNotNull(locations);
        assertCollectionSize(locations, 1);
        Location myLocation = locationManagementService.getMyLocationByIdAllData(locations.get(0).getId(), account.getToken());
    }

    @Test
    @Transactional
    public void getAllLocationTest() throws Exception {
        Location location = createLocation("GetAllLocationTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, null);
        List<Location> locations = locationManagementService.getAllLocations();

        Location location2 = createLocation("GetAllLocationTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location2, null);
        List<Location> locations2 = locationManagementService.getAllLocations();
        assertCollectionSize(locations2, locations.size() + 1);
    }

    @Test
    @Transactional
    public void getAllPrivateLocationTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "SaveToken", "jankowalski@email.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        Individual individual2 = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual2);
        UserAccount account2 = createUserAccount("Login2", "Password", "SaveToken2", "jankowalski2@email.com", UserAccount.Status.ACTIVE, individual2);
        userManagementService.saveUserAccount(account2);

        Individual individual3 = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual3);
        UserAccount account3 = createUserAccount("Login3", "Password", "SaveToken3", "jankowalski3@email.com", UserAccount.Status.ACTIVE, individual3);
        userManagementService.saveUserAccount(account3);

        locationManagementService.addNewPrivateLocation("Name", "Description", null, Location.Status.AVAILABLE, 10.0, 10.0, null, null, "City", "Country", account.getToken());
        locationManagementService.addNewPrivateLocation("Name2", "Description", null, Location.Status.AVAILABLE, 11.0, 10.0, null, null, "City", "Country", account.getToken());
        locationManagementService.addNewPrivateLocation("Name3", "Description", null, Location.Status.AVAILABLE, 12.0, 10.0, null, null, "City", "Country", account2.getToken());
        locationManagementService.addNewPrivateLocation("Name4", "Description", null, Location.Status.AVAILABLE, 13.0, 10.0, null, null, "City", "Country", account2.getToken());
        locationManagementService.addNewPrivateLocation("Name5", "Description", null, Location.Status.AVAILABLE, 14.0, 10.0, null, null, "City", "Country", account3.getToken());

        List<Location> locations = locationManagementService.getAllUsersPrivateLocations(account.getId());
        assertNotNull(locations);
        assertCollectionSize(locations, 2);
        List<Location> locations2 = locationManagementService.getAllUsersPrivateLocations(account2.getId());
        assertNotNull(locations2);
        assertCollectionSize(locations2, 2);
        List<Location> locations3 = locationManagementService.getAllUsersPrivateLocations(account3.getId());
        assertNotNull(locations3);
        assertCollectionSize(locations3, 1);
    }

    @Test
    @Transactional
    public void getLocationsInScopeTest() throws Exception {
        locationManagementService.saveLocation(createLocation("Name", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 11.0, 11.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 12.0, 12.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 15.0, 15.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 16.0, 16.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 17.0, 17.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 18.0, 18.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);
        locationManagementService.saveLocation(createLocation("Name", 20.0, 20.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska")), null);

        List<Location> locations = locationManagementService.getAllLocations();

        List<Location> scopeOneList = locationManagementService.getLocationInScope(15.0, 15.0, 5 * LocationManagementService.DEGREE_KILOMETERS_RATIO);
        assertNotNull(scopeOneList);
        assertCollectionSize(scopeOneList, 8);
        List<Location> scopeTwoList = locationManagementService.getLocationInScope(15.0, 15.0, 1 * LocationManagementService.DEGREE_KILOMETERS_RATIO);
        assertNotNull(scopeTwoList);
        assertCollectionSize(scopeTwoList, 2);
        List<Location> scopeThreeList = locationManagementService.getLocationInScope(17.0, 17.0, 3 * LocationManagementService.DEGREE_KILOMETERS_RATIO);
        assertNotNull(scopeThreeList);
        assertCollectionSize(scopeThreeList, 5);
        List<Location> scopeFourList = locationManagementService.getLocationInScope(17.0, 17.0, 1.5 * LocationManagementService.DEGREE_KILOMETERS_RATIO);
        assertNotNull(scopeFourList);
        assertCollectionSize(scopeFourList, 3);
    }

    @Test
    @Transactional
    public void changeLocationStatus() throws Exception {
        Location location = createLocation("GetAllLocationTest1", 10.0, 10.0, Location.Status.DRAFT, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(location, null);
        locationManagementService.changeLocationStatus(location.getId(), Location.Status.AVAILABLE);
        Location availableLocation = locationManagementService.getLocationById(location.getId());
        assertNotNull(availableLocation);
        assertEquals(availableLocation.getStatus(), Location.Status.AVAILABLE);
        locationManagementService.changeLocationStatus(location.getId(), Location.Status.UNAVAILABLE);
        Location unavailableLocation = locationManagementService.getLocationById(location.getId());
        assertNotNull(unavailableLocation);
        assertEquals(unavailableLocation.getStatus(), Location.Status.UNAVAILABLE);
        locationManagementService.changeLocationStatus(location.getId(), Location.Status.REMOVED);
        Location removedLocation = locationManagementService.getLocationById(location.getId());
        assertNull(removedLocation);

    }

    private void compareLocations(Location baseLocation, Location testedLocation) throws Exception {
        assertNotNull(testedLocation);
        assertEquals(baseLocation.getId(), testedLocation.getId());
        assertEquals(baseLocation.getName(), testedLocation.getName());
        assertEquals(baseLocation.getLatitude(), testedLocation.getLatitude(), 0.01);
        assertEquals(baseLocation.getLongitude(), testedLocation.getLongitude(), 0.01);
    }

    private void saveCommentExpectedError(Comment comment, ErrorMessages message) throws Exception {
        try {
            locationManagementService.saveComment(comment);
        } catch (FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }

    private void saveLocationExpectedError(Location location, ErrorMessages message) throws Exception {
        try {
            locationManagementService.saveLocation(location, null);
        } catch (FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }
}
