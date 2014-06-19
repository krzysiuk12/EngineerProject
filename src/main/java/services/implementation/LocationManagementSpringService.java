package services.implementation;

import domain.locations.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Repository
public class LocationManagementSpringService implements ILocationManagementService {

    private ILocationManagementRepository locationManagementRepository;
    private IUserManagementService userManagementService;

    public LocationManagementSpringService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationById(Long id) {
        Location location = locationManagementRepository.getLocationById(id);
        location.setCreatedByAccount(null);
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
        List<Location> locationsList = locationManagementRepository.getAllLocations();
        locationsList.forEach((location) -> location.setCreatedByAccount(null));
        return locationsList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        List<Location> privateLocationsList = locationManagementRepository.getAllUsersPrivateLocations(userId);
        privateLocationsList.forEach((location) -> location.setCreatedByAccount(null));
        return privateLocationsList;
    }

    @Override
    @Transactional
    public Location changeLocationStatus(Long locationId, Location.Status status) {
        Location location = getLocationByIdAllData(locationId);
        location.setStatus(status);
        locationManagementRepository.saveOrUpdateLocation(location);
        return location;
    }
}
