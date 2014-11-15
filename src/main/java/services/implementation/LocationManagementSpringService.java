package services.implementation;

import annotations.AdminAuthorized;
import domain.locations.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Service
public class LocationManagementSpringService implements ILocationManagementService {

    private static final double DEGREE_KILOMETERS_RATIO = 112.0;

    private ILocationManagementRepository locationManagementRepository;
    private IUserManagementService userManagementService;

    @Autowired
    public LocationManagementSpringService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
    }

    @Override
    @Transactional
    public void saveLocation(Location location) {
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
}
