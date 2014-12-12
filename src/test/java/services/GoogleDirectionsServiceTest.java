package services;

import common.BaseTestObject;
import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.TripDay;
import jsonserializers.google.directions.GoogleDirectionsSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.IGoogleDirectionsService;
import services.interfaces.ILocationManagementService;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class GoogleDirectionsServiceTest extends BaseTestObject {

    @Autowired
    private ILocationManagementService locationManagementService;

    @Autowired
    private IGoogleDirectionsService googleDirectionsService;

    @Test
    @Transactional
    public void getTripDestriptionTest() throws Exception {
        Location originLocation = createLocation("Origin", 50.054053, 19.935399, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(originLocation, null);
        Location destinationLocation = createLocation("Destination", 50.061894, 19.936735, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(destinationLocation, null);

        GoogleDirectionsSerializer serializer = googleDirectionsService.getTripDescription(originLocation, destinationLocation, TravelMode.WALKING, DistanceUnit.IMPERIAL, null);
        assertNotNull(serializer);
        assertNotNull(serializer.getRoutes());
    }

    @Test
    @Transactional
    public void testTripDeserialization() throws Exception {
        Location originLocation = createLocation("Origin", 50.054053, 19.935399, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(originLocation, null);
        Location destinationLocation = createLocation("Destination", 50.061894, 19.936735, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
        locationManagementService.saveLocation(destinationLocation, null);

        GoogleDirectionsSerializer serializer = googleDirectionsService.getTripDescription(originLocation, destinationLocation, TravelMode.WALKING, DistanceUnit.IMPERIAL, null);
        TripDay tripDay = googleDirectionsService.deserializeTripDescription(serializer, originLocation, destinationLocation, null, new Date());
        assertNotNull(tripDay);
        assertNotNull(tripDay.getLocations());
        assertNotNull(tripDay.getTripSteps());
        tripDay = googleDirectionsService.deserializeTripDescription(serializer, originLocation, destinationLocation, new ArrayList<>(), new Date());
        assertNotNull(tripDay);
        assertNotNull(tripDay.getLocations());
        assertNotNull(tripDay.getTripSteps());
    }
}
