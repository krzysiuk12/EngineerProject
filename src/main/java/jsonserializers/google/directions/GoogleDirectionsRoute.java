package jsonserializers.google.directions;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDirectionsRoute {

    private List<GoogleDirectionsLeg> legs;
    private String summary;
    private List<Integer> waypoint_order;

    public GoogleDirectionsRoute() {
    }

    public GoogleDirectionsRoute(List<GoogleDirectionsLeg> legs, String summary, List<Integer> waypoint_order) {
        this.legs = legs;
        this.summary = summary;
        this.waypoint_order = waypoint_order;
    }

    public List<GoogleDirectionsLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<GoogleDirectionsLeg> legs) {
        this.legs = legs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}


