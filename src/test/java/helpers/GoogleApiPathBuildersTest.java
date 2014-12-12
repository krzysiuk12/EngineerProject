package helpers;

import builders.GoogleDirectionsApiPathBuilder;
import builders.GoogleGeocodingApiPathBuilder;
import common.BaseTestObject;
import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class GoogleApiPathBuildersTest extends BaseTestObject {

    private static final String GOOGLE_DIRECTIONS_PATH = "https://maps.googleapis.com/maps/api/directions/json";
    private static final String GOOGLE_GEOCODING_PATH = "https://maps.googleapis.com/maps/api/geocode/json";

    @Test
    public void testGoogleDirectionsApiPathBuilder() {
        GoogleDirectionsApiPathBuilder googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder("Krakow", "Zakopane");
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=krakow&destination=zakopane");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder("KRaKoW", "ZAkOpAnE");
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=krakow&destination=zakopane");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam("DRIVING");
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=driving");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam(TravelMode.WALKING);
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=walking");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam(TravelMode.WALKING);
        googleDirectionsApiPathBuilder.addUnitParam("METRIC");
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=walking&units=metric");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam(TravelMode.WALKING);
        googleDirectionsApiPathBuilder.addUnitParam(DistanceUnit.IMPERIAL);
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=walking&units=imperial");
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam(TravelMode.WALKING);
        googleDirectionsApiPathBuilder.addUnitParam(DistanceUnit.IMPERIAL);
        googleDirectionsApiPathBuilder.addWaypointsParam("Warszawa,Poznan,Wroclaw");
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=walking&units=imperial&waypoints=warszawa,poznan,wroclaw");
        Collection<Location> locations = new ArrayList<Location>();
        locations.add(createMockLocation(10.0, 10.0));
        locations.add(createMockLocation(20.0, 20.0));
        googleDirectionsApiPathBuilder = new GoogleDirectionsApiPathBuilder(1.0, 1.0, 2.0, 2.0);
        googleDirectionsApiPathBuilder.addModeParam(TravelMode.WALKING);
        googleDirectionsApiPathBuilder.addUnitParam(DistanceUnit.IMPERIAL);
        googleDirectionsApiPathBuilder.addWaypointsParam(locations);
        assertEquals(googleDirectionsApiPathBuilder.build(), GOOGLE_DIRECTIONS_PATH + "?origin=1.0,1.0&destination=2.0,2.0&mode=walking&units=imperial&waypoints=10.0,10.0|20.0,20.0");
    }

    @Test
    public void testGoogleGeocodingApiPathBuilder() {
        GoogleGeocodingApiPathBuilder googleGeocodingApiPathBuilder = new GoogleGeocodingApiPathBuilder("Czarnowiejska,Krakow");
        assertEquals(googleGeocodingApiPathBuilder.build(), GOOGLE_GEOCODING_PATH + "?address=czarnowiejska,krakow");
        googleGeocodingApiPathBuilder = new GoogleGeocodingApiPathBuilder("Czarnowiejska,Krakow");
        googleGeocodingApiPathBuilder.addRegionParam("es");
        assertEquals(googleGeocodingApiPathBuilder.build(), GOOGLE_GEOCODING_PATH + "?address=czarnowiejska,krakow&region=es");
        googleGeocodingApiPathBuilder = new GoogleGeocodingApiPathBuilder("Czarnowiejska,Krakow");
        googleGeocodingApiPathBuilder.addComponentsParam("country:ES|route:RO");
        assertEquals(googleGeocodingApiPathBuilder.build(), GOOGLE_GEOCODING_PATH + "?address=czarnowiejska,krakow&components=country:es|route:ro");
    }



}
