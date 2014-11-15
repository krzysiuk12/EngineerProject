package jsonserializers.google.directions;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public enum GoogleDirectionsStatus {

    OK,
    NOT_FOUND,
    ZERO_RESULTS,
    MAX_WAYPOINTS_EXCEEDED,
    INVALID_REQUEST,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    UNKNOWN_ERROR

}
