package services;

import common.BaseTestObject;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.UserGroup;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IDataGeneratorService;
import tools.ConfigurationTools;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class DataGeneratorServiceTest extends BaseTestObject {

    @Autowired
    private IDataGeneratorService dataGeneratorService;

    public void simpleObjectCreationTest() {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createPasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAccountSecurityProfile(4, 32, 3, 30, 3);
        SecurityProfile securityProfile = dataGeneratorService.createSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        UserGroup userGroup = dataGeneratorService.createUserGroup("Name", "Desc", securityProfile);

    }


}
