package repository.implementation;

import domain.locations.Address;
import domain.locations.Location;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.interfaces.ILocationManagementRepository;

import java.util.List;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Repository
public class LocationManagementRepository extends BaseHibernateRepository implements ILocationManagementRepository {

    @Autowired
    public LocationManagementRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveOrUpdateLocation(Location location) {
        getCurrentSession().saveOrUpdate(location);
    }

    @Override
    public void saveOrUpdateAddress(Address address) {
        getCurrentSession().saveOrUpdate(address);
    }

    @Override
    public Location getLocationById(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.ne("status", Location.Status.REMOVED));
        return (Location)returnSingleOrNull(criteria.list());
    }

    @Override
    public Location getLocationByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("createdByAccount", FetchMode.JOIN);
        criteria.setFetchMode("createdByAccount.individual", FetchMode.JOIN);
        criteria.add(Restrictions.ne("status", Location.Status.REMOVED));
        return (Location)returnSingleOrNull(criteria.list());
    }

    @Override
    public List<Location> getAllLocations() {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.createAlias("createdByAccount", "createdByAccount", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("usersPrivate", false));
        criteria.add(Restrictions.ne("status", Location.Status.REMOVED));
        return criteria.list();
    }

    @Override
    public List<Location> getAllUsersPrivateLocations(Long userId) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.createAlias("createdByAccount", "createdByAccount", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("createdByAccount.id", userId));
        criteria.add(Restrictions.eq("usersPrivate", true));
        criteria.add(Restrictions.ne("status", Location.Status.REMOVED));
        return criteria.list();
    }

    @Override
    public List<Location> getLocationsInScope(double latitude, double longitude, double degreeScope) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.add(Restrictions.between("latitude", latitude - degreeScope, latitude + degreeScope));
        criteria.add(Restrictions.between("longitude", longitude - degreeScope, longitude + degreeScope));
        criteria.add(Restrictions.eq("usersPrivate", false));
        criteria.add(Restrictions.ne("status", Location.Status.REMOVED));
        return criteria.list();
    }

    @Override
    public List<Location> getAllLocationsByIds(List<Long> locationIds) {
        Criteria criteria = getCurrentSession().createCriteria(Location.class);
        criteria.add(Restrictions.in("id", locationIds));
        return criteria.list();
    }

}
