package repository.interfaces;

import domain.locations.Location;
import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementRepository extends IBaseHibernateRepository {

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);

    public List<Location> getAllLocations();
    public List<Location> getAllUsersPrivateLocations(UserAccount userAccount);
    public List<Location> getLocationsInScope(double longitude, double latitude, double scope);
    public List<Location> getAllLocationsByIds(List<Long> locationIds);

}
