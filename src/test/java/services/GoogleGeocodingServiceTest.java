package services;

import common.BaseTestObject;
import domain.locations.Location;
import jsonserializers.google.geocoding.GoogleGeocodingSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IGoogleGeocodingService;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class GoogleGeocodingServiceTest extends BaseTestObject {

    @Autowired
    private IGoogleGeocodingService googleGeocodingService;

    @Test
    @Transactional
    public void testGetLocationDescription() throws Exception {
        GoogleGeocodingSerializer serializer = googleGeocodingService.getLocationDescription("Krakow, Polska", null, null);
        assertNotNull(serializer);
        assertCollectionSize(serializer.getResults(), 1);

        serializer = googleGeocodingService.getLocationDescription("Krakow, Polska", "Malopolska", null);
        assertNotNull(serializer);
        assertCollectionSize(serializer.getResults(), 1);

        serializer = googleGeocodingService.getLocationDescription(null, "Malopolska", null);
        assertNull(serializer);
    }

    @Test
    @Transactional
    public void testLocationDeserialization() throws Exception {
        GoogleGeocodingSerializer serializer = googleGeocodingService.getLocationDescription("Krakow, Polska", null, null);
        Location descriptionLocation = googleGeocodingService.deserializeLocationDescription(serializer);
        assertNotNull(descriptionLocation);
        assertNotNull(descriptionLocation.getName());
        assertNotNull(descriptionLocation.getAddress().getCity());
        assertNotNull(descriptionLocation.getAddress().getCountry());
        Location coordinatesLocation = googleGeocodingService.deserializeLocationForLatLng(serializer);
        assertNotNull(coordinatesLocation);
        assertNull(coordinatesLocation.getName());
        assertNull(coordinatesLocation.getAddress());
    }
}
