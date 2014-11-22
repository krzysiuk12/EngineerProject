package services.interfaces;

import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.Trip;
import domain.trips.TripDay;
import jsonserializers.trips.TripDayCreationSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface ITripsManagementService {

    public void saveTripDay(TripDay tripDay) throws Exception;

    public TripDay addNewTripDay(Trip trip, Date day, Long originLocationId, Long destinationLocationId, List<Long> waypointIds, TravelMode travelMode, DistanceUnit distanceUnit) throws Exception;

    public Trip addNewTrip(String name, String desc, Date startDate, TravelMode mode, DistanceUnit unit, List<TripDayCreationSerializer> daysData, String userToken) throws Exception;

    public List<Trip> getAllUsersTrips(String token);

    public Trip getTripById(Long id);
    public Trip getTripByIdAllData(Long id);
    public List<Trip> getAllTrips();

    public TripDay getTripDayById(Long id);
    public TripDay getTripDayByIdAllData(Long id);

}
