package services.interfaces;

import domain.securityprofiles.SecurityProfile;

/**
 * Created by Krzysiu on 2014-06-18.
 */
public interface ISecurityProfileManagementService {

    public SecurityProfile getSecurityProfileByUserAccountId(Long userId);
    public SecurityProfile getSecurityProfileByUserGroupId(Long userGroupId);

}
