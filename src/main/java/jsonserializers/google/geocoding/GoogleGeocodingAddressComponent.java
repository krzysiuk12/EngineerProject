package jsonserializers.google.geocoding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;


/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingAddressComponent {

    private String long_name;
    private String short_name;
    private List<GoogleGeocodingAddressComponentType> types;

    public GoogleGeocodingAddressComponent() {
    }

    public GoogleGeocodingAddressComponent(String long_name, String short_name, List<GoogleGeocodingAddressComponentType> types) {
        this.long_name = long_name;
        this.short_name = short_name;
        this.types = types;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<GoogleGeocodingAddressComponentType> getTypes() {
        return types;
    }

    public void setTypes(List<GoogleGeocodingAddressComponentType> types) {
        this.types = types;
    }
}
