package services.interfaces;

import domain.locations.Address;
import domain.locations.Location;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementService {

    public void saveLocation(Location location) throws Exception;
    public void saveAddress(Address address) throws Exception;

    public void addNewLocation(String name, double longitude, double latitude, String addressCity, String addressCountry, String userToken) throws Exception;
    public void addNewPrivateLocation(String name, double longitude, double latitude, String addressCity, String addressCountry, String userToken) throws Exception;

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);
    public Location getMyLocationByIdAllData(Long id, String userToken);

    public List<Location> getAllLocations();
    public List<Location> getAllUsersPrivateLocations(Long userId);

    public List<Location> getLocationInScope(double latitude, double longitude, double kmScope) throws Exception;

    public Location changeLocationStatus(Long locationId, Location.Status status) throws Exception;

    public List<Location> getAllLocationsByIds(List<Long> locationIds) throws Exception;
}
