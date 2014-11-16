package domain.trips;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Embeddable
public class Coordinate {

    private double latitude;
    private double longitude;

    public Coordinate() {
    }

    public Coordinate(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    @Basic
    @Column(name = "latitude", nullable = false)
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false)
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
