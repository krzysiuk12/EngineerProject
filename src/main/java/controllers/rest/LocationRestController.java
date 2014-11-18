package controllers.rest;

import domain.locations.Location;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.locations.LocationSerializer;
import jsonserializers.locations.LocationStatusChangeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/locations")
public class LocationRestController {

    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;

    @Autowired
    public LocationRestController(ILocationManagementService locationManagementService, IUserManagementService userManagementService) {
        this.locationManagementService = locationManagementService;
        this.userManagementService = userManagementService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseSerializer addNewLocation(@RequestHeader(value = "authorization") String token, @RequestBody LocationSerializer data) throws Exception {
        try {
            locationManagementService.addNewLocation(data.getName(), data.getLongitude(), data.getLatitude(), data.getAddressCity(), data.getAddressCountry(), token);
            return new ResponseSerializer();
        } catch(Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    @RequestMapping(value = "/private", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseSerializer addNewPrivateLocation(@RequestHeader(value = "authorization") String token, @RequestBody LocationSerializer data) throws Exception {
        locationManagementService.addNewPrivateLocation(data.getName(), data.getLongitude(), data.getLatitude(), data.getAddressCity(), data.getAddressCountry(), token);
        return new ResponseSerializer();
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<List<Location>> getAllLocations(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(locationManagementService.getAllLocations());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<Location> getLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        Location location = locationManagementService.getLocationById(id);
        location.setCreatedByAccount(null);
        return new ResponseSerializer(location);
    }

    @RequestMapping(value = "/my/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<Location> getMyLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        Location location = locationManagementService.getMyLocationByIdAllData(id, token);
        if(location != null) {
            location.setCreatedByAccount(null);
        }
        return new ResponseSerializer(location);
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<Location> getLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        return new ResponseSerializer(locationManagementService.getLocationByIdAllData(id));
    }

    @RequestMapping(value = "/{locationId}/status", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseSerializer<Location> changeLocationStatusById(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long locationId, @RequestBody LocationStatusChangeSerializer locationStatusChangeSerializer) throws Exception {
        Location location = locationManagementService.changeLocationStatus(locationId, locationStatusChangeSerializer.getCurrentStatus());
        location.setCreatedByAccount(null);
        return new ResponseSerializer<>(location);
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Location updateLocationByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("locationId") Long id, @RequestBody Location location) throws Exception {
        //TODO: Create service + repository method for saving
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseSerializer deleteLocationById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        locationManagementService.changeLocationStatus(id, Location.Status.REMOVED);
        return new ResponseSerializer();
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public @ResponseBody ResponseSerializer<List<Location>> getAllUsersPrivateLocations(@RequestHeader(value = "authorization") String token) throws Exception {
        return new ResponseSerializer(locationManagementService.getAllUsersPrivateLocations(userManagementService.getUserAccountByToken(token).getId()));
    }

}
