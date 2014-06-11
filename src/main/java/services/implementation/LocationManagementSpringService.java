package services.implementation;

import domain.locations.Location;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public class LocationManagementSpringService extends BaseSpringService implements ILocationManagementService {

    private ILocationManagementRepository locationManagementRepository;
    private IUserManagementService userManagementService;

    public LocationManagementSpringService(ILocationManagementRepository locationManagementRepository, IUserManagementService userManagementService) {
        this.locationManagementRepository = locationManagementRepository;
        this.userManagementService = userManagementService;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationById(Long id) {
        return locationManagementRepository.getLocationById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocationByIdAllData(Long id) {
        return locationManagementRepository.getLocationByIdAllData(id);
    }
}
