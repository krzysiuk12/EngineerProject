package repository.implementation;

import org.hibernate.SessionFactory;
import repository.interfaces.ILocationManagementRepository;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public class LocationManagementHibernateRepository extends BaseHibernateRepository implements ILocationManagementRepository {

    public LocationManagementHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
