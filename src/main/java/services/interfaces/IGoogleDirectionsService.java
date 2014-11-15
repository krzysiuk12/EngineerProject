package services.interfaces;

import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.Trip;
import jsonserializers.google.directions.GoogleDirectionsSerializer;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public interface IGoogleDirectionsService {

    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, Collection<? extends Location> waypoints) throws Exception;

    public Trip deserializeTripDescription(GoogleDirectionsSerializer serializer) throws Exception;

}
