package controllers.rest;

import domain.locations.Location;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.google.geocoding.GoogleGeocodingSerializer;
import jsonserializers.google.request.GoogleGeocodeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.IFreeGeoService;
import services.interfaces.IGoogleDirectionsService;
import services.interfaces.IGoogleGeocodingService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
@Controller
@RequestMapping(value = "/googleapi")
public class GoogleRestController {

    private IGoogleGeocodingService googleGeocodingService;
    private IGoogleDirectionsService googleDirectionsService;
    private IFreeGeoService freeGeoService;

    @Autowired
    public GoogleRestController(IGoogleGeocodingService googleGeocodingService, IGoogleDirectionsService googleDirectionsService, IFreeGeoService freeGeoService) {
        this.googleGeocodingService = googleGeocodingService;
        this.googleDirectionsService = googleDirectionsService;
        this.freeGeoService = freeGeoService;
    }

    @RequestMapping(value = "/geocode/location", method = RequestMethod.POST)
    public @ResponseBody
    ResponseSerializer<Location> getLocationByAddress(@RequestHeader(value = "authorization") String token, @RequestBody GoogleGeocodeSerializer data) throws Exception {
        GoogleGeocodingSerializer geoSerializer = googleGeocodingService.getLocationDescription(data.getAddress(), data.getRegion(), data.getComponents());
        return new ResponseSerializer<>(googleGeocodingService.deserializeLocationDescription(geoSerializer));
    }

    @RequestMapping(value = "/geocode/coordinates", method = RequestMethod.POST)
    public @ResponseBody
    ResponseSerializer<Location> getCoordniatesByAddress(@RequestHeader(value = "authorization") String token, @RequestBody GoogleGeocodeSerializer data) throws Exception {
        GoogleGeocodingSerializer geoSerializer = googleGeocodingService.getLocationDescription(data.getAddress(), data.getRegion(), data.getComponents());
        return new ResponseSerializer<>(googleGeocodingService.deserializeLocationForLatLng(geoSerializer));
    }

    @RequestMapping(value = "/geocode/ip", method = RequestMethod.GET)
    public @ResponseBody
    ResponseSerializer<Location> getLocationByIp(@RequestHeader(value = "authorization") String token, HttpServletRequest request) throws Exception {
        return new ResponseSerializer<>(freeGeoService.getLocationByIp(request.getRemoteAddr()));
    }
}
