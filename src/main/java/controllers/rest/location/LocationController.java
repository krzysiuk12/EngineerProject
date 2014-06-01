package controllers.rest.location;

import domain.locations.Location;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public @ResponseBody
    Location getLocationInJson() {
        Location location = new Location();
        location.setName("LocationName");
        location.setLatitude(1.0);
        location.setLongitude(2.0);
        return location;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Location> getLocationListInJson() {
        List<Location> locations = new ArrayList<>();
        Location location = new Location();
        location.setName("LocationName");
        location.setLatitude(1.0);
        location.setLongitude(2.0);
        locations.add(location);
        location = new Location();
        location.setName("LocationName2");
        location.setLatitude(3.0);
        location.setLongitude(4.0);
        locations.add(location);
        return locations;
    }

}
