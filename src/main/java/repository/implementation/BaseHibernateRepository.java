package repository.implementation;

import domain.common.implementation.BaseObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public abstract class BaseHibernateRepository {

    private SessionFactory sessionFactory;

    protected BaseHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public <T extends BaseObject> void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public <T> T returnSingleOrNull(List<T> accounts) {
        return accounts != null && accounts.size() == 1 ? accounts.get(0) : null;
    }

}
