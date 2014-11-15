package controllers.rest;

import domain.locations.Location;
import exceptions.FormValidationError;
import jsonserializers.common.*;
import jsonserializers.common.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ILocationManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller(value = "locationRestController")
@RequestMapping("/locations")
public class LocationRestController {

    private ILocationManagementService locationManagementService;

    @Autowired
    public LocationRestController(ILocationManagementService locationManagementService) {
        this.locationManagementService = locationManagementService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<Location>> getAllLocations(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(locationManagementService.getAllLocations());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer addNewLocation(@RequestHeader(value = "authorization") String token, @RequestBody Location location) throws Exception  {
        locationManagementService.saveLocation(location);
        return new ResponseSerializer();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<Location> getLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        return new ResponseSerializer(locationManagementService.getLocationById(id));
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<Location> getLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception  {
        return new ResponseSerializer(locationManagementService.getLocationByIdAllData(id));
    }

    @RequestMapping(value = "/{locationId}/status", method = RequestMethod.PUT)
    public @ResponseBody Location changeLocationStatusById(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long locationId, @RequestBody Location.Status status) throws Exception  {
        return locationManagementService.changeLocationStatus(locationId, status);
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
    public @ResponseBody Location updateLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long id, @RequestBody Location location) throws Exception {
        //TODO: Create service + repository method for saving
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseSerializer deleteLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        locationManagementService.changeLocationStatus(id, Location.Status.REMOVED);
        return new ResponseSerializer();
    }

}
