package services.interfaces;


import domain.locations.Location;
import jsonserializers.google.geocoding.GoogleGeocodingSerializer;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public interface IGoogleGeocodingService {

    public GoogleGeocodingSerializer getLocationDescription(String address, String region, String components) throws Exception;

    public Location deserializeLocationDescription(GoogleGeocodingSerializer serializer) throws Exception;

    public Location deserializeLocationForLatLng(GoogleGeocodingSerializer serializer) throws Exception;

}
