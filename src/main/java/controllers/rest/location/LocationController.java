package controllers.rest.location;

import domain.Location;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public @ResponseBody Location getLocationInJson() {
        Location location = new Location();
        location.setName("LocationName");
        location.setLatitude(1.0);
        location.setLongitude(2.0);
        return location;
    }

}
