package repository.interfaces;

import domain.locations.Address;
import domain.locations.Location;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementRepository {

    public void saveOrUpdateLocation(Location location);
    public void saveOrUpdateAddress(Address address);

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);

    public List<Location> getAllLocations();
    public List<Location> getAllUsersPrivateLocations(Long userId);

    public List<Location> getLocationsInScope(double longitude, double latitude, double scope);

}
