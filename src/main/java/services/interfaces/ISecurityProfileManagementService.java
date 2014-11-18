package services.interfaces;

import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface ISecurityProfileManagementService {

    public void saveSecurityProfile(SecurityProfile securityProfile) throws Exception;

    public void saveAccountSecurityProfile(AccountSecurityProfile accountSecurityProfile) throws Exception;

    public void savePasswordSecurityProfile(PasswordSecurityProfile passwordSecurityProfile) throws Exception;

    public SecurityProfile getDefaultSecurityProfile();

}
