package repository.interfaces;

import domain.locations.Location;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementRepository {

    public void saveLocation(Location location);

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);

    public List<Location> getAllLocations();
    public List<Location> getAllUsersPrivateLocations(Long userId);

}
