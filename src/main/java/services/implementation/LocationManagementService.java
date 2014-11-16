package services.implementation;

import domain.locations.Address;
import domain.locations.Location;
import exceptions.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

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

    @Autowired
    public LocationManagementService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
    }

    @Override
    @Transactional
    public void saveLocation(Location location) throws Exception {
        locationManagementRepository.saveOrUpdateLocation(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationById(Long id) {
        Location location = locationManagementRepository.getLocationById(id);
        if(location != null) {
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
        for(Location location : resultList) {
            location.setCreatedByAccount(null);
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        List<Location> resultList = locationManagementRepository.getAllUsersPrivateLocations(userId);
        for(Location location : resultList) {
            location.setCreatedByAccount(null);
        }
        return resultList;
    }

    @Override
    @Transactional
    public Location changeLocationStatus(Long locationId, Location.Status status) {
        Location location = getLocationByIdAllData(locationId);
        location.setStatus(status);
        locationManagementRepository.saveOrUpdateLocation(location);
        return location;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationInScope(double latitude, double longitude, double kmScope) {
        return locationManagementRepository.getLocationsInScope(latitude, longitude, kmScope / DEGREE_KILOMETERS_RATIO);
    }

    private List<ErrorMessages> validateLocation(Location location) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(location.getName() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_NAME);
        }
/*        if(location.getLatitude()) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_LATITUDE);
        }
        if(location.getLongitude()) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_LONGITUDE);
        }*/
        if(location.getAddress() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_ADDRESS);
        }
        if(location.getStatus() == null) {
            errorMessages.add(ErrorMessages.INVALID_LOCATION_STATUS);
        }
        return errorMessages;
    }

    private List<ErrorMessages> validateAddress(Address address) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(address.getCity() == null) {
            errorMessages.add(ErrorMessages.INVALID_ADDRESS_CITY);
        }
        if(address.getCountry() == null) {
            errorMessages.add(ErrorMessages.INVALID_ADDRESS_COUNTRY);
        }
        return errorMessages;
    }
}
