package services.implementation;


import builders.GoogleDirectionsApiPathBuilder;
import domain.locations.Location;
import domain.trips.DistanceUnit;
import domain.trips.TravelMode;
import domain.trips.Trip;
import jsonserializers.google.directions.GoogleDirectionsSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import services.interfaces.IGoogleDirectionsService;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Service
public class GoogleDirectionsService implements IGoogleDirectionsService {

    @Override
    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, Collection<? extends Location> waypoints) throws Exception {
        try {
            GoogleDirectionsApiPathBuilder pathBuilder = new GoogleDirectionsApiPathBuilder(origin.getLatitude(), origin.getLongitude(), destination.getLatitude(), destination.getLongitude());
            if (mode != null) {
                pathBuilder.addModeParam(mode);
            }
            if (unit != null) {
                pathBuilder.addUnitParam(unit);
            }
            if (waypoints != null && !waypoints.isEmpty()) {
                pathBuilder.addWaypointsParam(waypoints);
            }
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(pathBuilder.build(), GoogleDirectionsSerializer.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //getLogService().error("", "Failed to getTripDescription.", ex);
            return null;
        }
    }

    @Override
    public Trip deserializeTripDescription(GoogleDirectionsSerializer serializer) throws Exception {
        return new Trip();
    }
}
