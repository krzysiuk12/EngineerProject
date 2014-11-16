package services;

import common.BaseTestObject;
import domain.locations.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ILocationManagementService;

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

    @Test
    public void saveOrUpdateLocationTest() {
        try {
            Location location = createLocation("SaveTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location);
            List<Location> locations = locationManagementService.getAllLocations();
            assertCollectionSize(locations, 1);

            Location location2 = createLocation("SaveTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location2);
            locations = locationManagementService.getAllLocations();
            assertCollectionSize(locations, 2);

            Location location3 = createLocation("SaveTest3", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location3);
            locations = locationManagementService.getAllLocations();
            assertCollectionSize(locations, 3);
        } catch(Exception ex) {
            fail("Failed in saveOrUpdateLocationTest.");
        }
    }

    @Test
    public void getLocationByIdTest() {
        try {
            Location location = createLocation("GetLocationByIdTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location);
            Location location2 = createLocation("GetLocationByIdTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location2);
            Location resultLocation = locationManagementService.getLocationById(location.getId());
            compareLocations(location, resultLocation);
            Location resultLocationAllData = locationManagementService.getLocationByIdAllData(location.getId());
            compareLocations(location, resultLocationAllData);
            Location resultLocation2 = locationManagementService.getLocationById(location2.getId());
            compareLocations(location2, resultLocation2);
            Location resultLocationAllData2 = locationManagementService.getLocationByIdAllData(location2.getId());
            compareLocations(location2, resultLocationAllData2);
        } catch(Exception ex) {
            fail("Failed in getLocationByIdTest.");
        }
    }

    @Test
    public void getAllLocationTest() {
        try {
            Location location = createLocation("GetAllLocationTest1", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location);
            List<Location> locations = locationManagementService.getAllLocations();

            Location location2 = createLocation("GetAllLocationTest2", 10.0, 10.0, Location.Status.AVAILABLE, createAddress("Krakow", "Polska"));
            locationManagementService.saveLocation(location2);
            List<Location> locations2 = locationManagementService.getAllLocations();
            assertCollectionSize(locations2, locations.size() + 1);
        } catch(Exception ex) {
            fail("Failed in getAllLocationTest.");
        }
    }

    private void compareLocations(Location baseLocation, Location testedLocation) throws Exception {
        assertNotNull(testedLocation);
        assertEquals(baseLocation.getId(), testedLocation.getId());
        assertEquals(baseLocation.getName(), testedLocation.getName());
        assertEquals(baseLocation.getLatitude(), testedLocation.getLatitude(), 0.01);
        assertEquals(baseLocation.getLongitude(), testedLocation.getLongitude(), 0.01);
    }
}
