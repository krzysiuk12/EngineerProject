package jsonserializers.google.geocoding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingGeometry {

    private Coordinate location;

    public GoogleGeocodingGeometry() {
    }

    public GoogleGeocodingGeometry(Coordinate location) {
        this.location = location;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }
}
