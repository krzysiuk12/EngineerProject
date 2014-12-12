package services;

import common.BaseTestObject;
import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.Trip;
import domain.trips.TripDay;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import jsonserializers.trips.TripDayCreationSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ILocationManagementService;
import services.interfaces.ITripsManagementService;
import services.interfaces.IUserManagementService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class TripsManagementServiceTest extends BaseTestObject {

    @Autowired
    private IUserManagementService userManagementService;

    @Autowired
    private ITripsManagementService tripsManagementService;

    @Autowired
    private ILocationManagementService locationManagementService;

    @Test
    @Transactional
    public void addNewTripTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        Location originLocation = createLocation("Origin", 50.054053, 19.935399, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(originLocation, null);
        Location destinationLocation = createLocation("Destination", 50.061894, 19.936735, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(destinationLocation, null);
        TripDayCreationSerializer tdcs = new TripDayCreationSerializer();
        tdcs.setOriginLocationId(originLocation.getId());
        tdcs.setDestinationLocationId(destinationLocation.getId());

        String name = "TripName";
        String description = "TripDescription";
        Trip trip = tripsManagementService.addNewTrip(name, description, new Date(), TravelMode.WALKING, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken());
        assertNotNull(trip);
        assertEquals(trip.getName(), name);
        assertEquals(trip.getDescription(), description);

        addTripExpectedError(null, description, new Date(), TravelMode.WALKING, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken(), ErrorMessages.INVALID_TRIP_NAME);
        addTripExpectedError(name, null, new Date(), TravelMode.WALKING, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken(), ErrorMessages.INVALID_TRIP_DESCRIPTION);
        addTripExpectedError(name, description, null, TravelMode.WALKING, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken(), ErrorMessages.INVALID_TRIP_START_DATE);
        addTripExpectedError(name, description, new Date(), null, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken(), ErrorMessages.INVALID_TRIP_MODE);
        addTripExpectedError(name, description, new Date(), TravelMode.WALKING, null, new ArrayList<>(Arrays.asList(tdcs)), account.getToken(), ErrorMessages.INVALID_TRIP_UNIT);
        addTripExpectedError(name, description, new Date(), TravelMode.WALKING, DistanceUnit.IMPERIAL, null, account.getToken(), ErrorMessages.INVALID_TRIP_TRIP_DAYS);
    }

    @Test
    @Transactional
    public void tripsGettersTest() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("LoginUserLogin", "LoginUserPassword", "LoginUserToken", "LoginUserEmail", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        Location originLocation = createLocation("Origin", 50.054053, 19.935399, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(originLocation, null);
        Location destinationLocation = createLocation("Destination", 50.061894, 19.936735, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(destinationLocation, null);
        TripDayCreationSerializer tdcs = new TripDayCreationSerializer();
        tdcs.setOriginLocationId(originLocation.getId());
        tdcs.setDestinationLocationId(destinationLocation.getId());

        String name = "TripName";
        String description = "TripDescription";
        Trip trip = tripsManagementService.addNewTrip(name, description, new Date(), TravelMode.WALKING, DistanceUnit.IMPERIAL, new ArrayList<>(Arrays.asList(tdcs)), account.getToken());

        Trip byIdTrip = tripsManagementService.getTripById(trip.getId());
        assertNotNull(byIdTrip);
        assertEquals(byIdTrip.getId(), trip.getId());

        Trip byIdAllDataTrip = tripsManagementService.getTripByIdAllData(trip.getId());
        assertNotNull(byIdAllDataTrip);
        assertEquals(byIdAllDataTrip.getId(), trip.getId());

        List<Trip> allTrips = tripsManagementService.getAllTrips();
        assertNotNull(allTrips);
        assertCollectionSize(allTrips, 1);

        List<Trip> allUsersTrips = tripsManagementService.getAllUsersTrips(account.getToken());
        assertNotNull(allUsersTrips);
        assertCollectionSize(allUsersTrips, 1);

        Individual individualWithNoTrips = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individualWithNoTrips);
        UserAccount accountWithNoTrips = createUserAccount("LoginUserLogin2", "LoginUserPassword", "LoginUserToken", "LoginUserEmail2", UserAccount.Status.ACTIVE, individualWithNoTrips);
        userManagementService.saveUserAccount(accountWithNoTrips);

        allUsersTrips = tripsManagementService.getAllUsersTrips(accountWithNoTrips.getToken());
        assertNotNull(allUsersTrips);
        assertCollectionSize(allUsersTrips, 0);

        TripDay day = tripsManagementService.getTripDayById(byIdAllDataTrip.getDays().get(0).getId());
        assertNotNull(day);

        day = tripsManagementService.getTripDayByIdAllData(byIdAllDataTrip.getDays().get(0).getId());
        assertNotNull(day);
        assertNotNull(day.getTripSteps());
    }

    private void addTripExpectedError(String name, String desc, Date startDate, TravelMode mode, DistanceUnit unit, List<TripDayCreationSerializer> daysData, String token, ErrorMessages message) throws Exception {
        try {
            tripsManagementService.addNewTrip(name, desc, startDate, mode, unit, daysData, token);
        } catch(FormValidationError ex) {
            assertTrue(ex.getErrorMessages().contains(message));
        }
    }


}
