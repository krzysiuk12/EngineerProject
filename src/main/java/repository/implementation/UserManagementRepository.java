package repository.implementation;

import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.interfaces.IUserManagementRepository;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Repository
public class UserManagementRepository extends BaseHibernateRepository implements IUserManagementRepository {

    @Autowired
    public UserManagementRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    //<editor-fold desc="Get UserAccount (Id, Token, Login)">
    @Override
    public UserAccount getUserAccountById(Long id) {
        return (UserAccount)getCurrentSession().get(UserAccount.class, id);
    }

    @Override
    public UserAccount getUserAccountByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.createAlias("individual", "individual", JoinType.INNER_JOIN);
        return (UserAccount)returnSingleOrNull(criteria.list());
    }

    @Override
    public UserAccount getUserAccountByLogin(String login) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("login", login));
        return (UserAccount)returnSingleOrNull(criteria.list());
    }

    @Override
    public UserAccount getUserAccountByToken(String token) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("token", token));
        return (UserAccount)returnSingleOrNull(criteria.list());
    }
    //</editor-fold>

    //<editor-fold desc="Get All UserAccounts">
    @Override
    public List<UserAccount> getAllUserAccounts() {
        return getCurrentSession().createCriteria(UserAccount.class).list();
    }
    //</editor-fold>

    //<editor-fold desc="Validate Unique (Login, Email)">
    @Override
    public boolean validateUniqueLogin(String login) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("login", login));
        List<UserAccount> list = criteria.list();
        return list.isEmpty();
    }

    @Override
    public boolean validateUniqueEmail(String email) {
        Criteria criteria = getCurrentSession().createCriteria(UserAccount.class);
        criteria.add(Restrictions.eq("email", email));
        return criteria.list().isEmpty();
    }
    //</editor-fold>

    //<editor-fold desc="Get UserGroup (Id, Name)">
    @Override
    public UserGroup getUserGroupById(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(UserGroup.class);
        criteria.add(Restrictions.eq("id", id));
        return (UserGroup)returnSingleOrNull(criteria.list());
    }

    @Override
    public UserGroup getUserGroupByName(String name) {
        Criteria criteria = getCurrentSession().createCriteria(UserGroup.class);
        criteria.add(Restrictions.eq("name", name));
        return (UserGroup)returnSingleOrNull(criteria.list());
    }
    //</editor-fold>
}
