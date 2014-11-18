package services.implementation;


import builders.GoogleDirectionsApiPathBuilder;
import domain.locations.Location;
import domain.trips.*;
import jsonserializers.google.directions.GoogleDirectionsLeg;
import jsonserializers.google.directions.GoogleDirectionsRoute;
import jsonserializers.google.directions.GoogleDirectionsSerializer;
import jsonserializers.google.directions.GoogleDirectionsStep;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import services.interfaces.IGoogleDirectionsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Service
public class GoogleDirectionsService implements IGoogleDirectionsService {

    @Override
    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, List<Location> waypoints) throws Exception {
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
    public TripDay deserializeTripDescription(GoogleDirectionsSerializer serializer, Location origin, Location destination, List<Location> waypoints, Date day) throws Exception {
        GoogleDirectionsRoute route = serializer.getRoutes().get(0);
        List<Location> sortedLocations = sortLocationInRoute(origin, destination, waypoints, route.getWaypoint_order());
        TripDay tripDay = new TripDay();
        tripDay.setDate(day);
        createTripDayLocations(tripDay, sortedLocations);
        createTripSteps(tripDay, sortedLocations, route.getLegs());
        return tripDay;
    }

    private void createTripDayLocations(TripDay tripDay, List<Location> locations) {
        tripDay.setLocations(new ArrayList<>());
        for(int index = 0; index < locations.size(); index++) {
            tripDay.getLocations().add(new TripDayLocation(tripDay, locations.get(index), index));
        }
    }

    private void createTripSteps(TripDay tripDay, List<Location> locations, List<GoogleDirectionsLeg> leg) {
        tripDay.setTripSteps(new ArrayList<>());
        for(int index = 0; index < locations.size() - 1; index++) {
            GoogleDirectionsLeg currentLeg = leg.get(index);
            TripStep tripStep = new TripStep();
            tripStep.setTripDay(tripDay);
            tripStep.setStartLocation(locations.get(index));
            tripStep.setEndLocation(locations.get(index + 1));
            tripStep.setDistance(currentLeg.getDistance().getText());
            tripStep.setDistanceUnit(DistanceUnit.IMPERIAL);
            tripStep.setDuration(currentLeg.getDuration().getText());
            createTripDirections(tripStep, currentLeg.getSteps());
            tripDay.getTripSteps().add(tripStep);
        }
    }

    private void createTripDirections(TripStep tripStep, List<GoogleDirectionsStep> steps) {
        tripStep.setDirections(new ArrayList<>());
        for(int index = 0; index < steps.size(); index++) {
            GoogleDirectionsStep currentStep = steps.get(index);
            TripDirection tripDirection = new TripDirection();
            tripDirection.setTripStep(tripStep);
            tripDirection.setOrdinal(index);
            tripDirection.setDuration(currentStep.getDuration().getText());
            tripDirection.setDistance(currentStep.getDistance().getText());
            tripDirection.setInstruction(currentStep.getHtml_instructions());
            tripDirection.setTravelMode(currentStep.getTravel_mode());
            tripDirection.setStartCoordinate(new Coordinate(currentStep.getStart_location().getLat(), currentStep.getStart_location().getLng()));
            tripDirection.setEndCoordinate(new Coordinate(currentStep.getEnd_location().getLat(), currentStep.getEnd_location().getLng()));
            tripStep.getDirections().add(tripDirection);
        }
    }

    private List<Location> sortLocationInRoute(Location origin, Location destination, List<Location> waypointLocations, List<Integer> waypointOrder) {
        List<Location> result = new ArrayList<>();
        result.add(origin);
        if (waypointOrder != null && !waypointOrder.isEmpty()) {
            for (Integer ord : waypointOrder) {
                result.add(waypointLocations.get(ord));
            }
        }
        result.add(destination);
        return result;
    }
}
