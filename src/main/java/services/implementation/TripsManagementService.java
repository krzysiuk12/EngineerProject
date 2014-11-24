package services.implementation;

import domain.locations.Location;
import domain.trips.*;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
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

import java.util.*;

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

        for (TripDayLocation dayLocation : locations) {
            tripsManagementRepository.saveTripDayLocation(dayLocation);
        }

        for (TripStep step : tripSteps) {
            List<TripDirection> directions = step.getDirections();
            step.setDirections(null);
            tripsManagementRepository.saveTripStep(step);
            for (TripDirection direction : directions) {
                tripsManagementRepository.saveTripDirection(direction);
            }
            step.setDirections(directions);
        }
        tripDay.setTripSteps(tripSteps);
        tripDay.setLocations(locations);
    }

    @Override
    @Transactional
    public Trip addNewTrip(String name, String desc, Date startDate, TravelMode mode, DistanceUnit unit, List<TripDayCreationSerializer> daysData, String userToken) throws Exception {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(name == null) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_NAME);
        }
        if(desc == null) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_DESCRIPTION);
        }
        if(startDate == null) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_START_DATE);
        }
        if(mode == null) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_MODE);
        }
        if(unit == null) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_UNIT);
        }
        if(daysData == null || daysData.isEmpty()) {
            errorMessages.add(ErrorMessages.INVALID_TRIP_TRIP_DAYS);
        }
        if(!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }

        Trip trip = new Trip();
        trip.setName(name);
        trip.setDescription(desc);
        trip.setStartDate(startDate);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, daysData.size() - 1);
        trip.setEndDate(calendar.getTime());

        trip.setAuthor(userManagementService.getUserAccountByToken(userToken));
        tripsManagementRepository.saveTrip(trip);

        calendar.setTime(startDate);
        trip.setDays(new ArrayList<>());
        for (TripDayCreationSerializer day : daysData) {
            trip.getDays().add(addNewTripDay(trip, calendar.getTime(), day.getOriginLocationId(), day.getDestinationLocationId(), day.getWaypointLocationIds(), mode, unit));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return trip;
    }

    @Override
    @Transactional
    public TripDay addNewTripDay(Trip trip, Date day, Long originLocationId, Long destinationLocationId, List<Long> waypointIds, TravelMode travelMode, DistanceUnit distanceUnit) throws Exception {
        Location originLocation = locationManagementService.getLocationById(originLocationId);
        Location destinationLocation = locationManagementService.getLocationById(destinationLocationId);
        List<Location> waypointLocations = locationManagementService.getAllLocationsByIds(waypointIds);

        GoogleDirectionsSerializer serializer = googleDirectionsService.getTripDescription(originLocation, destinationLocation, travelMode, distanceUnit, waypointLocations);
        TripDay tripDay = googleDirectionsService.deserializeTripDescription(serializer, originLocation, destinationLocation, waypointLocations, day);
        tripDay.setTrip(trip);
        saveTripDay(tripDay);
        return tripDay;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trip> getAllUsersTrips(String token) {
        return tripsManagementRepository.getAllUsersTrips(userManagementService.getUserAccountByToken(token));
    }

    @Override
    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripsManagementRepository.getTripById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Trip getTripByIdAllData(Long id) {
        return tripsManagementRepository.getTripByIdAllData(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        return tripsManagementRepository.getAllTrips();
    }

    @Override
    @Transactional(readOnly = true)
    public TripDay getTripDayById(Long id) {
        return tripsManagementRepository.getTripDayById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TripDay getTripDayByIdAllData(Long id) {
        TripDay tripDay = tripsManagementRepository.getTripDayByIdAllData(id);
        return tripDay;
    }



}
