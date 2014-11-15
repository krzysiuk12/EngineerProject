package controllers.rest;

import domain.locations.Location;
import jsonserializers.common.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ILocationManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-13.
 */
@Controller(value = "coordinatesRestController")
@RequestMapping(value = "/coordinates")
public class CoordinatesRestController {

    private static final double DEFAULT_KM_SCOPE = 50.0;
    private ILocationManagementService locationManagementService;

    @Autowired
    public CoordinatesRestController(ILocationManagementService locationManagementService) {
        this.locationManagementService = locationManagementService;
    }

    @RequestMapping(value = "/{latitude}/{longitude}", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<Location>> getAllLocationsForCoordinates(@RequestHeader(value = "authorization") String token, @PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
        return new ResponseSerializer(locationManagementService.getLocationInScope(latitude, longitude, DEFAULT_KM_SCOPE));
    }


}
