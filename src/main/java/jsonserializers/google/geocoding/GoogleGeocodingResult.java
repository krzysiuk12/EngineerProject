package jsonserializers.google.geocoding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingResult {

    private List<GoogleGeocodingAddressComponent> address_components;
    private String formatted_address;
    private GoogleGeocodingGeometry geometry;
/*    private List<Type> types;*/

    public GoogleGeocodingResult() {
    }

    public GoogleGeocodingResult(List<GoogleGeocodingAddressComponent> address_components, String formatted_address, GoogleGeocodingGeometry geometry) { //, List<Type> types) {
        this.address_components = address_components;
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        //this.types = types;
    }

    public List<GoogleGeocodingAddressComponent> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<GoogleGeocodingAddressComponent> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GoogleGeocodingGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleGeocodingGeometry geometry) {
        this.geometry = geometry;
    }

/*    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }*/
}
