package services.interfaces;

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

    public void addNewTripDay(Trip trip, Date day, Long originLocationId, Long destinationLocationId, List<Long> waypointIds) throws Exception;

    public void addNewTrip(String name, String desc, Date startDate, Date endDate, List<TripDayCreationSerializer> daysData, String userToken) throws Exception;

}
