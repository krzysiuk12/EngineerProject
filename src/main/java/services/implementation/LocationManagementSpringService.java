package services.implementation;

import domain.locations.Location;
import domain.useraccounts.Address;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ILocationManagementRepository;
import services.interfaces.ILocationManagementService;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public class LocationManagementSpringService extends BaseSpringService implements ILocationManagementService {

    private ILocationManagementRepository locationManagementRepository;

    public LocationManagementSpringService(ILocationManagementRepository locationManagementRepository) {
        this.locationManagementRepository = locationManagementRepository;
    }

    @Override
    @Transactional
    public void createNewLocation() {
        Location location = new Location();
        location.setName("New Location");
        location.setDescription("Description");
        location.setAddress(new Address("street", "city", "postalcode", "country"));
        locationManagementRepository.saveLocation(location);
    }
}
