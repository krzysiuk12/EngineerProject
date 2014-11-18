package repository.interfaces;

import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface ISecurityProfilesManagementRepository {

    public void saveSecurityProfile(SecurityProfile securityProfile);

    public void saveAccountSecurityProfile(AccountSecurityProfile accountSecurityProfile);

    public void savePasswordSecurityProfile(PasswordSecurityProfile passwordSecurityProfile);

    public SecurityProfile getDefaultSecurityProfile();

}
