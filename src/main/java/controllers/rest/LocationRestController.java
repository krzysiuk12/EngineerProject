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
        return locationManagementService.getAllLocations();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewLocation(@RequestHeader(value = "authorization") String token, @RequestBody Location location) {
        //TODO: Save location, should return id, mobile should have its own db id + server id
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Location getLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) {
        return locationManagementService.getLocationById(id);
    }

    @RequestMapping(value = "/{locationId}/status", method = RequestMethod.PUT)
    public @ResponseBody Location changeLocationStatusById(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long locationId, @RequestBody Location.Status status) {
        return locationManagementService.changeLocationStatus(locationId, status);
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
    public @ResponseBody Location updateLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long id, @RequestBody Location location) {
        //TODO: Create service + repository method for saving
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
