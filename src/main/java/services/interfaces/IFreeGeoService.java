package services.interfaces;

import domain.locations.Location;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface IFreeGeoService {

    public Location getLocationByIp(String ipAddress);

}
