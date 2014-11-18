package services.implementation;

import domain.locations.Address;
import domain.locations.Location;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.ILoggerService;
import services.interfaces.IUserManagementService;
import tools.ValidationTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Service
public class LocationManagementService implements ILocationManagementService {

    private static final double DEGREE_KILOMETERS_RATIO = 112.0;

    private ILocationManagementRepository locationManagementRepository;
    private IUserManagementService userManagementService;
    private ILoggerService loggerService;

    @Autowired
    public LocationManagementService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService, ILoggerService loggerService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
        this.loggerService = loggerService;
    }

    @Override
    @Transactional
    public void saveLocation(Location location) throws Exception {
        List<ErrorMessages> errorMessages = validateLocation(location);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        locationManagementRepository.saveOrUpdateLocation(location);
    }

    @Override
    public void saveAddress(Address address) throws Exception {
        List<ErrorMessages> errorMessages = validateAddress(address);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        locationManagementRepository.saveOrUpdateAddress(address);
    }

    @Override
    @Transactional
    public void addNewLocation(String name, double longitude, double latitude, String addressCity, String addressCountry, String userToken) throws Exception {
        UserAccount createdBy = userManagementService.getUserAccountByToken(userToken);
        Location location = createLocation(name, latitude, longitude, false, createAddress(addressCity, addressCountry), createdBy);
        saveLocation(location);
    }

    @Override
    @Transactional
    public void addNewPrivateLocation(String name, double longitude, double latitude, String addressCity, String addressCountry, String userToken) throws Exception {
        UserAccount createdBy = userManagementService.getUserAccountByToken(userToken);
        Location location = createLocation(name, latitude, longitude, true, createAddress(addressCity, addressCountry), createdBy);
        saveLocation(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationById(Long id) {
        Location location = locationManagementRepository.getLocationById(id);
        if (location != null) {
            location.setCreatedByAccount(null);
        }
        return location;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationByIdAllData(Long id) {
        return locationManagementRepository.getLocationByIdAllData(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllLocations() {
        List<Location> resultList = locationManagementRepository.getAllLocations();
        for (Location location : resultList) {
            location.setCreatedByAccount(null);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        List<Location> resultList = locationManagementRepository.getAllUsersPrivateLocations(userId);
        for (Location location : resultList) {
            location.setCreatedByAccount(null);
        }
        return resultList;
    }

    @Override
    @Transactional
    public Location changeLocationStatus(Long locationId, Location.Status status) throws Exception {
        if (status == null) {
            throw new FormValidationError(ErrorMessages.INVALID_LOCATION_CURRENT_STATUS);
        }
        Location location = getLocationByIdAllData(locationId);
        location.setStatus(status);
        locationManagementRepository.saveOrUpdateLocation(location);
        return location;
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
    public Location getMyLocationByIdAllData(Long id, String userToken) {
        UserAccount userAccount = userManagementService.getUserAccountByIdAllData(userManagementService.getUserAccountByToken(userToken).getId());
        Location location = locationManagementRepository.getLocationByIdAllData(id);
        if (location != null && location.isUsersPrivate() && location.getCreatedByAccount().getId() == userAccount.getId()) {
            return location;
        }
        return null;
    }

    @Override
    @Transactional
    public List<Location> getAllLocationsByIds(List<Long> locationIds) throws Exception {
        return locationManagementRepository.getAllLocationsByIds(locationIds);
    }

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

    protected Address createAddress(String city, String country) {
        Address address = new Address();
        address.setCity(city);
        address.setCountry(country);
        return address;
    }

    private Location createLocation(String name, double latitude, double longitude, boolean usersPrivate, Address address, UserAccount createdBy) {
        Location location = new Location();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setStatus(Location.Status.AVAILABLE);
        location.setUsersPrivate(usersPrivate);
        location.setAddress(address);
        location.updateInformation(createdBy);
        return location;
    }

}
