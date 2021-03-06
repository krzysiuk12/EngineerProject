package services.implementation;

import builders.GoogleGeocodingApiPathBuilder;
import domain.locations.Address;
import domain.locations.Location;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import jsonserializers.google.geocoding.GoogleGeocodingAddressComponent;
import jsonserializers.google.geocoding.GoogleGeocodingAddressComponentType;
import jsonserializers.google.geocoding.GoogleGeocodingResult;
import jsonserializers.google.geocoding.GoogleGeocodingSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import services.interfaces.IGoogleGeocodingService;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@Service
public class GoogleGeocodingService implements IGoogleGeocodingService {

    private class AddressInformationComponents {

        private static final String ESTABLISHMENT_TYPE = "establishment";
        private static final String STREET_NUMBER_TYPE = "street_number";
        private static final String ROUTE_TYPE = "route";
        private static final String LOCALITY_TYPE = "locality";
        private static final String COUNTRY_TYPE = "country";
        private static final String POSTAL_CODE_TYPE = "postal_code";

        private String establishment;
        private String streetNumber;
        private String route;

        public String getEstablishment() {
            return establishment;
        }

        public void setEstablishment(String establishment) {
            this.establishment = establishment;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

    }

    @Override
    public GoogleGeocodingSerializer getLocationDescription(String address, String region, String components) throws Exception {
        try {
            if(address == null) {
                throw new FormValidationError(ErrorMessages.INVALID_GOOGLE_GEOCODE_ADDRESS);
            }
            GoogleGeocodingApiPathBuilder pathBuilder = new GoogleGeocodingApiPathBuilder(address);
            if (region != null) {
                pathBuilder.addRegionParam(region);
            }
            if (components != null) {
                pathBuilder.addComponentsParam(components);
            }
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(pathBuilder.build(), GoogleGeocodingSerializer.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Location deserializeLocationDescription(GoogleGeocodingSerializer serializer) throws Exception {
        Location location = new Location();
        location.setAddress(new Address());
        AddressInformationComponents aIC = new AddressInformationComponents();
        if (serializer != null && serializer.getResults() != null && !serializer.getResults().isEmpty()) {
            GoogleGeocodingResult result = serializer.getResults().get(0);
            for (GoogleGeocodingAddressComponent addressComponent : result.getAddress_components()) {
                if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.establishment)) {
                    aIC.setEstablishment(addressComponent.getLong_name());
                } else if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.street_number)) {
                    aIC.setStreetNumber(addressComponent.getLong_name());
                } else if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.route)) {
                    aIC.setRoute(addressComponent.getLong_name());
                } else if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.locality)) {
                    location.getAddress().setCity(addressComponent.getLong_name());
                } else if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.postal_code)) {
                    location.getAddress().setPostalCode(addressComponent.getLong_name());
                } else if (addressComponent.getTypes().contains(GoogleGeocodingAddressComponentType.country)) {
                    location.getAddress().setCountry(addressComponent.getLong_name());
                }
            }
            location.setLatitude(serializer.getResults().get(0).getGeometry().getLocation().getLat());
            location.setLongitude(serializer.getResults().get(0).getGeometry().getLocation().getLng());
            location.setName(aIC.getEstablishment() != null ? aIC.getEstablishment() : location.getAddress().getCity());
            location.setDescription(aIC.getEstablishment() != null ? aIC.getEstablishment() : location.getAddress().getCity());
            location.getAddress().setStreet(aIC.getRoute() != null ? aIC.getRoute() : ""  + (aIC.getStreetNumber() != null ? " " + aIC.getStreetNumber() : ""));
        }
        return location;
    }

    @Override
    public Location deserializeLocationForLatLng(GoogleGeocodingSerializer serializer) throws Exception {
        Location location = new Location();
        if (serializer != null && serializer.getResults() != null && !serializer.getResults().isEmpty()) {
            location.setLatitude(serializer.getResults().get(0).getGeometry().getLocation().getLat());
            location.setLongitude(serializer.getResults().get(0).getGeometry().getLocation().getLng());
        }
        return location;
    }
}
