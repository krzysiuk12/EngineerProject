package controllers.rest.location;

import domain.locations.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.interfaces.ILocationManagementService;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    private ILocationManagementService locationManagementService;

    @Autowired
    public LocationController(ILocationManagementService locationManagementService) {
        this.locationManagementService = locationManagementService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Location getLocationById(@PathVariable("id") Long id) {
        Location location = locationManagementService.getLocationById(id);
        location.setCreatedByAccount(null);
        return location;
    }
    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public @ResponseBody Location getLocationByIdAllData(@PathVariable("id") Long id) {
        return locationManagementService.getLocationByIdAllData(id);
    }

}
