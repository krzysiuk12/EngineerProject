package controllers.rest;

import domain.locations.Location;
import jsonserializers.google.directions.GoogleDirectionsSerializer;
import jsonserializers.google.geocoding.GoogleGeocodingSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.interfaces.IGoogleDirectionsService;
import services.interfaces.IGoogleGeocodingService;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
@Controller(value = "googleRestController")
@RequestMapping(value = "/google")
public class GoogleRestController {

    private IGoogleGeocodingService googleGeocodingService;
    private IGoogleDirectionsService googleDirectionsService;

    @Autowired
    public GoogleRestController(IGoogleGeocodingService googleGeocodingService, IGoogleDirectionsService googleDirectionsService) {
        this.googleGeocodingService = googleGeocodingService;
        this.googleDirectionsService = googleDirectionsService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody void test(@RequestHeader(value = "authorization") String token) throws Exception {
        GoogleGeocodingSerializer geoSerializer = googleGeocodingService.getLocationDescription("santacruz", null, null);

        Location origin = new Location();
        origin.setLatitude(50.067265);
        origin.setLongitude(19.944448);
        Location destination = new Location();
        destination.setLatitude(50.435275);
        destination.setLongitude(18.850237);
        GoogleDirectionsSerializer directionsSerializer = googleDirectionsService.getTripDescription(origin, destination, null, null, null);
        System.out.println("HERE");
    }
}
