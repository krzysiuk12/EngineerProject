package services.interfaces;

import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.TripDay;
import jsonserializers.google.directions.GoogleDirectionsSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public interface IGoogleDirectionsService {

    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, List<Location> waypoints) throws Exception;

    public TripDay deserializeTripDescription(GoogleDirectionsSerializer serializer, Location origin, Location destination, List<Location> waypoints, Date day) throws Exception;

}
