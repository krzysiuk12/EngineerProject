package repository.interfaces;

import domain.locations.Location;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementRepository {

    public void saveLocation(Location location);

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);

}
