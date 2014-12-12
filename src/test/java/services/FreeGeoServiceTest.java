package services;

import common.BaseTestObject;
import domain.locations.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IFreeGeoService;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class FreeGeoServiceTest extends BaseTestObject {

    @Autowired
    private IFreeGeoService freeGeoService;

    @Test
    @Transactional
    public void getLocationByIdTest() throws Exception {
        String ipAddress = "87.206.243.190";
        Location location = freeGeoService.getLocationByIp(ipAddress);
        assertNotNull(location);
    }


}
