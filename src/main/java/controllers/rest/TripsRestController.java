package controllers.rest;

import annotations.AdminAuthorized;
import domain.trips.Trip;
import domain.trips.TripDay;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.trips.TripCreationSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ITripsManagementService;

import java.util.List;

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

    @AdminAuthorized
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<List<Trip>> getTrips(@RequestHeader(value = "authorization") String token) throws Exception {
        List<Trip> trips = tripsManagementService.getAllTrips();
        prepareTripToNotAllDataRequest(trips);
        return new ResponseSerializer<>(trips);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseSerializer<Trip> addNewTrip(@RequestHeader(value = "authorization") String token, @RequestBody TripCreationSerializer tcs) throws Exception {
        Trip trip = tripsManagementService.addNewTrip(tcs.getName(), tcs.getDescription(), tcs.getStartDate(), tcs.getTravelMode(), tcs.getDistanceUnit(), tcs.getTripDays(), token);
        prepareTripToNotAllDataRequest(trip);
        return new ResponseSerializer<>(trip);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<Trip> getTripById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        Trip trip = tripsManagementService.getTripById(id);
        prepareTripToNotAllDataRequest(trip);
        return new ResponseSerializer<>(trip);
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<Trip> getTripByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        return new ResponseSerializer<>(tripsManagementService.getTripByIdAllData(id));
    }

    @RequestMapping(value = "/day/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<TripDay> getTripDayById(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        TripDay tripDay = tripsManagementService.getTripDayById(id);
        prepareTripDayToNoAllDataRequest(tripDay);
        return new ResponseSerializer<>(tripDay);
    }

    @RequestMapping(value = "/day/{id}/all", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<TripDay> getTripDayByIdAllData(@RequestHeader(value = "authorization") String token, @PathVariable("id") Long id) throws Exception {
        return new ResponseSerializer<>(tripsManagementService.getTripDayByIdAllData(id));
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseSerializer<List<Trip>> getAllUserTrips(@RequestHeader(value = "authorization") String token) throws Exception {
        List<Trip> trips = tripsManagementService.getAllUsersTrips(token);
        prepareTripToNotAllDataRequest(trips);
        return new ResponseSerializer<>(trips);
    }

    private void prepareTripDayToNoAllDataRequest(TripDay tripDay) {
        tripDay.setTripSteps(null);
    }

    private void prepareTripToNotAllDataRequest(Trip trip) {
        for (TripDay day : trip.getDays()) {
            prepareTripDayToNoAllDataRequest(day);
        }
        trip.setAuthor(null);
    }

    private void prepareTripToNotAllDataRequest(List<Trip> trips) {
        for (Trip trip : trips) {
            prepareTripToNotAllDataRequest(trip);
        }
    }
}
