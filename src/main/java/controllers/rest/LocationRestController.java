package controllers.rest;

import domain.locations.Location;
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
    public @ResponseBody List<Location> getAllLocations(@RequestHeader(value = "authorization") String token) {
        //TODO: Return all localizations, method only accessed by administrator
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewLocation(@RequestHeader(value = "authorization") String token, @RequestBody Location location) {
        //TODO: Save location, should return id, mobile should have its own db id + server id
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Location getLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        Location location = locationManagementService.getLocationById(id);
        location.setCreatedByAccount(null);
        return location;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public @ResponseBody Location updateLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id, @RequestBody String locationData) {
        //TODO: Create service + repositry method for saving
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody Location updateLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id, @RequestBody Location location) {
        //TODO: Create service + repositry method for saving
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        //TODO: Create service + repositry method for saving
        return null;
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public @ResponseBody Location getLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        return locationManagementService.getLocationByIdAllData(id);
    }

}
