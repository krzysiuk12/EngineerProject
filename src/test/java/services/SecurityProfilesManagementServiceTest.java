package services;

import common.BaseTestObject;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IDataGeneratorService;
import services.interfaces.ISecurityProfileManagementService;
import tools.ConfigurationTools;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class SecurityProfilesManagementServiceTest extends BaseTestObject {

    @Autowired
    private ISecurityProfileManagementService securityProfileManagementService;

    @Autowired
    private IDataGeneratorService dataGeneratorService;

    @Test
    @Transactional
    public void saveAndGetSecurityProfilesTest() throws Exception {
        PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createPasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
        securityProfileManagementService.savePasswordSecurityProfile(passwordSecurityProfile);
        AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAccountSecurityProfile(4, 32, 3, 30, 3);
        securityProfileManagementService.saveAccountSecurityProfile(accountSecurityProfile);
        SecurityProfile securityProfile = dataGeneratorService.createSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
        securityProfileManagementService.saveSecurityProfile(securityProfile);

        SecurityProfile testSecurityProfile = securityProfileManagementService.getDefaultSecurityProfile();
        assertNotNull(testSecurityProfile);
        assertNotNull(testSecurityProfile.getAccountSecurityProfile());
        assertNotNull(testSecurityProfile.getPasswordSecurityProfile());
        assertEquals(testSecurityProfile.getId(), securityProfile.getId());
        assertEquals(testSecurityProfile.getAccountSecurityProfile().getId(), accountSecurityProfile.getId());
        assertEquals(testSecurityProfile.getPasswordSecurityProfile().getId(), passwordSecurityProfile.getId());
    }

}
