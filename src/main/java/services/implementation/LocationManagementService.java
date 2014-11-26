package services.implementation;

import domain.locations.Address;
import domain.locations.Comment;
import domain.locations.Location;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;
import tools.ValidationTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Service
public class LocationManagementService implements ILocationManagementService {

    private static final double DEGREE_KILOMETERS_RATIO = 112.0;

    private ILocationManagementRepository locationManagementRepository;
    private IUserManagementService userManagementService;

    @Autowired
    public LocationManagementService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
    }

    //<editor-fold desc="Save (Location, Comment)">
    @Override
    @Transactional
    public void saveLocation(Location location, UserAccount executor) throws Exception {
        List<ErrorMessages> errorMessages = validateLocation(location);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        location.updateInformation(executor);
        locationManagementRepository.saveOrUpdate(location);
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) throws Exception {
        List<ErrorMessages> errorMessages = validateComment(comment);
        if(!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        locationManagementRepository.saveOrUpdate(comment);
    }
    //</editor-fold>

    //<editor-fold desc="Add Locations (Normal, Private)">
    @Override
    @Transactional
    public void addNewLocation(String name, String description, String url, Location.Status status, double longitude, double latitude, String street, String postalcode, String city, String country, String userToken) throws Exception {
        UserAccount createdBy = userManagementService.getUserAccountByToken(userToken);
        Address address = createAddress(street, postalcode, city, country);
        Location location = createLocation(name, description, url, status, latitude, longitude, false, address, createdBy);
        saveLocation(location, createdBy);
    }

    @Override
    @Transactional
    public void addNewPrivateLocation(String name, String description, String url, Location.Status status, double longitude, double latitude, String street, String postalcode, String city, String country, String userToken) throws Exception {
        UserAccount createdBy = userManagementService.getUserAccountByToken(userToken);
        Address address = createAddress(street, postalcode, city, country);
        Location location = createLocation(name, description, url, status, latitude, longitude, true, address, createdBy);
        saveLocation(location, createdBy);
    }
    //</editor-fold>

    //<editor-fold desc="Add New Comment">
    @Override
    @Transactional
    public void addNewComment(Long locationId, Comment.Rating rating, String commentMessage, String userToken) throws Exception {
        UserAccount createdBy = userManagementService.getUserAccountByToken(userToken);
        Location location = getLocationByIdAllData(locationId);
        Comment comment = createComment(location, createdBy, rating, commentMessage);
        location.setRating(getCurrentLocationRating(location, rating));
        saveComment(comment);
        saveLocation(location, createdBy);
    }
    //</editor-fold>

    //<editor-fold desc="Get Locations By Id">
    @Override
    @Transactional(readOnly = true)
    public Location getLocationById(Long id) {
        Location location = locationManagementRepository.getLocationById(id);
        if (location != null) {
            preperLocationToNotAllDataRequest(location);
        }
        return location;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationByIdAllData(Long id) {
        return locationManagementRepository.getLocationByIdAllData(id);
    }

    @Override
    @Transactional
    public Location getMyLocationByIdAllData(Long id, String userToken) {
        UserAccount userAccount = userManagementService.getUserAccountByIdAllData(userManagementService.getUserAccountByToken(userToken).getId());
        Location location = locationManagementRepository.getLocationByIdAllData(id);
        if (location != null && location.isUsersPrivate() && location.getCreatedByAccount().getId() == userAccount.getId()) {
            return location;
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Get Locations Lists">
    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllLocations() {
        List<Location> resultList = locationManagementRepository.getAllLocations();
        for (Location location : resultList) {
            preperLocationToNotAllDataRequest(location);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        UserAccount createdBy = userManagementService.getUserAccountById(userId);
        List<Location> resultList = locationManagementRepository.getAllUsersPrivateLocations(createdBy);
        for (Location location : resultList) {
            preperLocationToNotAllDataRequest(location);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationInScope(double latitude, double longitude, double kmScope) throws Exception {
        List<ErrorMessages> errorMessages = validateCoordinates(latitude, longitude);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        return locationManagementRepository.getLocationsInScope(latitude, longitude, kmScope / DEGREE_KILOMETERS_RATIO);
    }

    @Override
    @Transactional
    public List<Location> getAllLocationsByIds(List<Long> locationIds) throws Exception {
        return locationManagementRepository.getAllLocationsByIds(locationIds);
    }
    //</editor-fold>

    //<editor-fold desc="Change Location Status">
    @Override
    @Transactional
    public Location changeLocationStatus(Long locationId, Location.Status status) throws Exception {
        if (status == null) {
            throw new FormValidationError(ErrorMessages.INVALID_LOCATION_CURRENT_STATUS);
        }
        Location location = getLocationByIdAllData(locationId);
        location.setStatus(status);
        locationManagementRepository.saveOrUpdate(location);
        return location;
    }
    //</editor-fold>

    //<editor-fold desc="Validation (Location, Coordinates, Address)">
    private List<ErrorMessages> validateLocation(Location location) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if (location.getName() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_NAME);
        }
        if (location.getStatus() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_STATUS);
        }
        if (location.getAddress() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_ADDRESS);
        }
        if (location.getAddress() != null) {
            errorMessages.addAll(validateAddress(location.getAddress()));
        }
        errorMessages.addAll(validateCoordinates(location.getLatitude(), location.getLongitude()));
        return errorMessages;
    }

    private List<ErrorMessages> validateCoordinates(double latitude, double longitude) {
        List<ErrorMessages> errorMessages = new ArrayList<ErrorMessages>();
        if (!ValidationTools.validateLatitude(latitude)) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_LATITUDE);
        }
        if (!ValidationTools.validateLongitude(longitude)) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_LONGITUDE);
        }
        return errorMessages;
    }


    private List<ErrorMessages> validateAddress(Address address) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if (address.getCity() == null) {
            errorMessages.add(ErrorMessages.INVALID_ADDRESS_CITY);
        }
        if (address.getCountry() == null) {
            errorMessages.add(ErrorMessages.INVALID_ADDRESS_COUNTRY);
        }
        return errorMessages;
    }

    private List<ErrorMessages> validateComment(Comment comment) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(comment.getUserAccount() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_ACCOUNT);
        }
        if(comment.getLocation() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION);
        }
        if(comment.getRating() == null) {
            errorMessages.add(ErrorMessages.INVALID_RATING);
        }
        if(comment.getDate() == null) {
            errorMessages.add(ErrorMessages.INVALID_DATE);
        }
        return errorMessages;
    }
    //</editor-fold>

    private void preperLocationToNotAllDataRequest(Location location) {
        location.setCreatedByAccount(null);
        location.setComments(null);
    }

    //<editor-fold desc="Creation Helper Methods">
    protected Address createAddress(String street, String postalCode, String city, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    private Location createLocation(String name, String description, String url, Location.Status status, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setUrl(url);
        location.setStatus(status != null ? status : Location.Status.AVAILABLE);
        location.setUsersPrivate(usersPrivate);
        location.setAddress(address);
        location.updateInformation(createdBy);
        return location;
    }

    private Comment createComment(Location location, UserAccount account, Comment.Rating rating, String commentMessage) {
        Comment comment = new Comment();
        comment.setLocation(location);
        comment.setUserAccount(account);
        comment.setRating(rating);
        comment.setComment(commentMessage);
        comment.setDate(new Date());
        return comment;
    }

    private double getCurrentLocationRating(Location location, Comment.Rating rating) {
        double ratingValue = rating.getValue();
        double currentRating = location.getRating();
        double numberOfComments = location.getComments().size() + 1;
        double difference = (ratingValue - currentRating) / numberOfComments;
        return location.getRating() + difference;
    }
    //</editor-fold>
}
