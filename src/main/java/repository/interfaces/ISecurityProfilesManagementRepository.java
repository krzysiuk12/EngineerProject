package repository.interfaces;

import domain.securityprofiles.SecurityProfile;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface ISecurityProfilesManagementRepository extends IBaseHibernateRepository {

    public SecurityProfile getDefaultSecurityProfile();

}
