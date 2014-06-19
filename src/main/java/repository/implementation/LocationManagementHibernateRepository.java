package repository.implementation;

import domain.locations.Location;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import repository.interfaces.ILocationManagementRepository;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
public class LocationManagementHibernateRepository extends BaseHibernateRepository implements ILocationManagementRepository {

    public LocationManagementHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveOrUpdateLocation(Location location) {
        getCurrentSession().saveOrUpdate(location);
    }

    @Override
    public Location getLocationById(Long id) {
        return (Location)getCurrentSession().get(Location.class, id);
    }

    @Override
    public Location getLocationByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("createdByAccount", FetchMode.JOIN);
        criteria.setFetchMode("createdByAccount.individual", FetchMode.JOIN);
        return (Location)criteria.list().get(0);
    }

    @Override
    public List<Location> getAllLocations() {
        return getCurrentSession().createCriteria(Location.class).list();
    }

    @Override
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.createAlias("createdByAccount", "createdByAccount", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("createdByAccount.id", userId));
        criteria.add(Restrictions.eq("usersPrivate", true));
        return criteria.list();
    }
}
