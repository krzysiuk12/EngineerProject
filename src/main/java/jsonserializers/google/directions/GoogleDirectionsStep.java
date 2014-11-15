package jsonserializers.google.directions;

import domain.trips.TravelMode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDirectionsStep {

    private TravelMode travel_mode;
    private Coordinate start_location;
    private Coordinate end_location;
    private GoogleDirectionsTextAndValue duration;
    private String html_instructions;
    private GoogleDirectionsTextAndValue distance;

    public GoogleDirectionsStep() {
    }

    public GoogleDirectionsStep(GoogleDirectionsTextAndValue distance, GoogleDirectionsTextAndValue duration, Coordinate end_location, Coordinate start_location, TravelMode travel_mode, String html_instructions) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
        this.travel_mode = travel_mode;
        this.html_instructions = html_instructions;
    }

    public GoogleDirectionsTextAndValue getDistance() {
        return distance;
    }

    public void setDistance(GoogleDirectionsTextAndValue distance) {
        this.distance = distance;
    }

    public GoogleDirectionsTextAndValue getDuration() {
        return duration;
    }

    public void setDuration(GoogleDirectionsTextAndValue duration) {
        this.duration = duration;
    }

    public Coordinate getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public Coordinate getStart_location() {
        return start_location;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public TravelMode getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(TravelMode travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }
}
