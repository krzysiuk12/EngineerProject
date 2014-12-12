package services;

import common.BaseTestObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ILoggerService;

/**
 * Created by Krzysztof Kicinger on 2014-12-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class LoggerServiceTest extends BaseTestObject {

    @Autowired
    private ILoggerService loggerService;

    @Test
    public void testLogMessages() {
        loggerService.info("Info Message");
        loggerService.warn("Warn Message");
        loggerService.error("Error Message");
        loggerService.debug("Debug Message");
        loggerService.error("Error with Exception Message", new Exception());
    }

}
