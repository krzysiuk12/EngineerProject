package jsonserializers.locations;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Krzysztof Kicinger on 2014-11-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSerializer {

    private String name;
    private double latitude;
    private double longitude;
    private String addressCity;
    private String addressCountry;

    public LocationSerializer() {
    }

    public LocationSerializer(String name, double latitude, double longitude, String addressCity, String addressCountry) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }
}
