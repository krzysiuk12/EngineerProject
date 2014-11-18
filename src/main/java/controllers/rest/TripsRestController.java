package controllers.rest;

import jsonserializers.common.ResponseSerializer;
import jsonserializers.trips.TripCreationSerializer;
import jsonserializers.trips.TripDayCreationSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ITripsManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Controller
@RequestMapping("/trips")
public class TripsRestController {

    private ITripsManagementService tripsManagementService;

    @Autowired
    public TripsRestController(ITripsManagementService tripsManagementService) {
        this.tripsManagementService = tripsManagementService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    TripCreationSerializer getTrips(@RequestHeader(value = "authorization") String token) throws Exception {
        TripCreationSerializer tcs = new TripCreationSerializer();
        tcs.setName("TripName");
        tcs.setDescription("TripDescription");
        tcs.setStartDate(new Date());
        tcs.setEndDate(new Date());
        tcs.setTripDays(new ArrayList<>());
        tcs.getTripDays().add(new TripDayCreationSerializer(1, 2, new ArrayList<Long>(Arrays.asList(3L, 4L, 5L))));
        tcs.getTripDays().add(new TripDayCreationSerializer(6, 7, new ArrayList<Long>(Arrays.asList(8L, 9L, 10L))));
        tcs.getTripDays().add(new TripDayCreationSerializer(11, 12, new ArrayList<Long>(Arrays.asList(13L, 14L, 15L))));
        return tcs;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseSerializer addNewTrip(@RequestHeader(value = "authorization") String token, @RequestBody TripCreationSerializer tcs) throws Exception {
        tripsManagementService.addNewTrip(tcs.getName(), tcs.getDescription(), tcs.getStartDate(), tcs.getEndDate(), tcs.getTripDays(), token);
        return new ResponseSerializer();
    }

}
