import helpers.GoogleApiPathBuildersTest;
import helpers.ValidationToolsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import services.*;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GoogleApiPathBuildersTest.class, ValidationToolsTest.class, CodeGeneratorServiceTest.class, FreeGeoServiceTest.class, GoogleDirectionsServiceTest.class, GoogleGeocodingServiceTest.class, EventsServiceTest.class, LoggerServiceTest.class, LocationManagementServiceTest.class, SecurityProfilesManagementServiceTest.class, SessionManagementServiceTest.class, TripsManagementServiceTest.class, UserManagementServiceTest.class})
public class MainTestSuite {
}
