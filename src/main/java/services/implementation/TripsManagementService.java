package services.implementation;

import domain.locations.Location;
import domain.trips.*;
import jsonserializers.google.directions.GoogleDirectionsSerializer;
import jsonserializers.trips.TripDayCreationSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ITripsManagementRepository;
import services.interfaces.IGoogleDirectionsService;
import services.interfaces.ILocationManagementService;
import services.interfaces.ITripsManagementService;
import services.interfaces.IUserManagementService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
public class TripsManagementService implements ITripsManagementService {

    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;
    private IGoogleDirectionsService googleDirectionsService;
    private ITripsManagementRepository tripsManagementRepository;

    @Autowired
    public TripsManagementService(IUserManagementService userManagementService, ILocationManagementService locationManagementService, IGoogleDirectionsService googleDirectionsService, ITripsManagementRepository tripsManagementRepository) {
        this.userManagementService = userManagementService;
        this.locationManagementService = locationManagementService;
        this.googleDirectionsService = googleDirectionsService;
        this.tripsManagementRepository = tripsManagementRepository;
    }

    @Override
    @Transactional
    public void saveTripDay(TripDay tripDay) throws Exception {
        List<TripDayLocation> locations = tripDay.getLocations();
        List<TripStep> tripSteps = tripDay.getTripSteps();
        tripDay.setTripSteps(null);
        tripDay.setLocations(null);
        tripsManagementRepository.saveTripDay(tripDay);

        for(TripDayLocation dayLocation : locations) {
            tripsManagementRepository.saveTripDayLocation(dayLocation);
        }

        for(TripStep step : tripSteps) {
            List<TripDirection> directions = step.getDirections();
            step.setDirections(null);
            tripsManagementRepository.saveTripStep(step);
            for(TripDirection direction : directions) {
                tripsManagementRepository.saveTripDirection(direction);
            }
            step.setDirections(directions);
        }
        tripDay.setTripSteps(tripSteps);
        tripDay.setLocations(locations);
    }

    @Override
    @Transactional
    public void addNewTrip(String name, String desc, Date startDate, Date endDate, List<TripDayCreationSerializer> daysData, String userToken) throws Exception {
        Trip trip = new Trip();
        trip.setName(name);
        trip.setDescription(desc);
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setAuthor(userManagementService.getUserAccountByToken(userToken));
        tripsManagementRepository.saveTrip(trip);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        for(TripDayCreationSerializer day : daysData) {
            addNewTripDay(trip, calendar.getTime(), day.getOriginLocationId(), day.getDestinationLocationId(), day.getWaypointLocationIds());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    @Transactional
    public void addNewTripDay(Trip trip, Date day, Long originLocationId, Long destinationLocationId, List<Long> waypointIds) throws Exception {
        Location originLocation = locationManagementService.getLocationById(originLocationId);
        Location destinationLocation = locationManagementService.getLocationById(destinationLocationId);
        List<Location> waypointLocations = locationManagementService.getAllLocationsByIds(waypointIds);

        GoogleDirectionsSerializer serializer = googleDirectionsService.getTripDescription(originLocation, destinationLocation, null, null, waypointLocations);
        TripDay tripDay = googleDirectionsService.deserializeTripDescription(serializer, originLocation, destinationLocation, waypointLocations, day);
        tripDay.setTrip(trip);
        saveTripDay(tripDay);
    }
}
