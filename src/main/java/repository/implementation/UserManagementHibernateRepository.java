package repository.implementation;

import domain.useraccounts.UserAccount;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import repository.interfaces.IUserManagementRepository;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public class UserManagementHibernateRepository extends BaseHibernateRepository implements IUserManagementRepository {

    public UserManagementHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveUserAccount(UserAccount account) {
        getCurrentSession().save(account);
    }

    @Override
    public UserAccount getUserAccountById(Long id) {
        return (UserAccount)getCurrentSession().get(UserAccount.class, id);
    }

    @Override
    public UserAccount getUserAccountByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("individual", FetchMode.JOIN);
        return (UserAccount)criteria.list().get(0);
    }
}
