package builders;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public class FreeGeoIpPathBuilder extends PathBuilder {

    private static final String FREE_GEO_SERVER_PATH = "http://freegeoip.net/json/";

    public FreeGeoIpPathBuilder(String ipAddress) {
        pathBuilder.append(FREE_GEO_SERVER_PATH).append(ipAddress);
    }

}
