package repository.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

}
