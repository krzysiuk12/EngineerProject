package services.implementation;

import builders.FreeGeoIpPathBuilder;
import domain.locations.Address;
import domain.locations.Location;
import jsonserializers.freegio.FreeGeoSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import services.interfaces.IFreeGeoService;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
public class FreeGeoService implements IFreeGeoService {

    @Override
    public Location getLocationByIp(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        return createLocation(restTemplate.getForObject(new FreeGeoIpPathBuilder(ipAddress).build(), FreeGeoSerializer.class));
    }

    private Location createLocation(FreeGeoSerializer serializer) {
        String city = serializer.getCity().split(" ")[0];
        Location location = new Location();
        location.setName(city);
        location.setDescription(city + " " + serializer.getCountry_name());
        location.setLatitude(serializer.getLatitude());
        location.setLongitude(serializer.getLongitude());
        location.setStatus(Location.Status.AVAILABLE);
        location.setAddress(createAddress(city, serializer.getCountry_name()));
        return location;
    }

    protected Address createAddress(String city, String country) {
        Address address = new Address();
        address.setCity(city);
        address.setCountry(country);
        return address;
    }


}
