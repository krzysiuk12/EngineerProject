package services.interfaces;

import domain.locations.Comment;
import domain.locations.Location;
import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public interface ILocationManagementService {

    public void saveLocation(Location location, UserAccount executor) throws Exception;
    public void saveComment(Comment comment) throws Exception;

    public void addNewLocation(String name, String description, double longitude, double latitude, String street, String postalcode, String addressCity, String addressCountry, String userToken) throws Exception;
    public void addNewPrivateLocation(String name, String description, double longitude, double latitude, String street, String postalcode, String city, String country, String userToken) throws Exception;
    public void addNewComment(Long locationId, Comment.Rating rating, String comment, String userToken) throws Exception;

    public Location getLocationById(Long id);
    public Location getLocationByIdAllData(Long id);
    public Location getMyLocationByIdAllData(Long id, String userToken);

    public List<Location> getAllLocations();
    public List<Location> getAllUsersPrivateLocations(Long userId);

    public List<Location> getLocationInScope(double latitude, double longitude, double kmScope) throws Exception;

    public Location changeLocationStatus(Long locationId, Location.Status status) throws Exception;

    public List<Location> getAllLocationsByIds(List<Long> locationIds) throws Exception;
}
