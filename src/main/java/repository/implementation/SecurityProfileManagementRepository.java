package repository.implementation;

import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.interfaces.ISecurityProfilesManagementRepository;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Repository
public class SecurityProfileManagementRepository extends BaseHibernateRepository implements ISecurityProfilesManagementRepository {

    @Autowired
    public SecurityProfileManagementRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveSecurityProfile(SecurityProfile securityProfile) {
        getCurrentSession().saveOrUpdate(securityProfile);
    }

    @Override
    public void saveAccountSecurityProfile(AccountSecurityProfile accountSecurityProfile) {
        getCurrentSession().saveOrUpdate(accountSecurityProfile);
    }

    @Override
    public void savePasswordSecurityProfile(PasswordSecurityProfile passwordSecurityProfile) {
        getCurrentSession().saveOrUpdate(passwordSecurityProfile);
    }

    @Override
    public SecurityProfile getDefaultSecurityProfile() {
        Criteria criteria = getCurrentSession().createCriteria(SecurityProfile.class);
        criteria.add(Restrictions.eq("defaultProfile", true));
        return (SecurityProfile) returnSingleOrNull(criteria.list());
    }

}
