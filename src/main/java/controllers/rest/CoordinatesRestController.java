package controllers.rest;

import domain.locations.Location;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-13.
 */
@Controller(value = "coordinatesRestController")
@RequestMapping(value = "/coordinates")
public class CoordinatesRestController {

    @RequestMapping(value = "/{latitude}/{longitude}", method = RequestMethod.GET)
    public @ResponseBody List<Location> getAllLocationsForCoordinates(@RequestHeader(value = "authorization") String token) {
        //TODO: Implementacja z wykorzystanie Hibernate Spatial
        return null;
    }


}
