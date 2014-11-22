package repository.implementation;

import domain.trips.*;
import domain.useraccounts.UserAccount;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.interfaces.ITripsManagementRepository;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Repository
public class TripsManagementRepository extends BaseHibernateRepository implements ITripsManagementRepository {

    @Autowired
    public TripsManagementRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveTrip(Trip trip) {
        getCurrentSession().saveOrUpdate(trip);
    }

    @Override
    public void saveTripDay(TripDay tripDay) {
        getCurrentSession().saveOrUpdate(tripDay);
    }

    @Override
    public void saveTripDayLocation(TripDayLocation tripDayLocation) {
        getCurrentSession().saveOrUpdate(tripDayLocation);
    }

    @Override
    public void saveTripDirection(TripDirection tripDirection) {
        getCurrentSession().saveOrUpdate(tripDirection);
    }

    @Override
    public void saveTripStep(TripStep tripStep) {
        getCurrentSession().saveOrUpdate(tripStep);
    }

    @Override
    public Trip getUserTripById(UserAccount userAccount, Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.add(Restrictions.eq("author", userAccount));
        criteria.add(Restrictions.eq("id", id));
        return (Trip)returnSingleOrNull(criteria.list());
    }

    @Override
    public Trip getUserTripByIdAllData(UserAccount userAccount, Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.add(Restrictions.eq("author", userAccount));
        criteria.add(Restrictions.eq("id", id));
        criteria.createAlias("days", "days", JoinType.INNER_JOIN);
        criteria.createAlias("days.locations", "locations", JoinType.INNER_JOIN);
        criteria.createAlias("days.tripSteps", "tripSteps", JoinType.INNER_JOIN);
        criteria.createAlias("tripSteps.directions", "directions", JoinType.INNER_JOIN);
        criteria.createAlias("author", "author", JoinType.INNER_JOIN);
        criteria.createAlias("author.individual", "individual", JoinType.INNER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Trip)returnSingleOrNull(criteria.list());
    }

    @Override
    public List<Trip> getAllUsersTrips(UserAccount userAccount) {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.add(Restrictions.eq("author", userAccount));
        criteria.createAlias("days", "days", JoinType.INNER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public Trip getTripById(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.createAlias("days", "days", JoinType.INNER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Trip)returnSingleOrNull(criteria.list());
    }

    @Override
    public Trip getTripByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.createAlias("days.tripSteps", "tripSteps", JoinType.INNER_JOIN);
        criteria.createAlias("tripSteps.directions", "directions", JoinType.INNER_JOIN);
        criteria.createAlias("author", "author", JoinType.INNER_JOIN);
        criteria.createAlias("author.individual", "individual", JoinType.INNER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Trip)returnSingleOrNull(criteria.list());
    }

    @Override
    public List<Trip> getAllTrips() {
        Criteria criteria = getCurrentSession().createCriteria(Trip.class);
        criteria.createAlias("days", "days", JoinType.INNER_JOIN);
        return criteria.list();
    }

    @Override
    public TripDay getTripDayById(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(TripDay.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.createAlias("locations", "locations", JoinType.INNER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (TripDay)returnSingleOrNull(criteria.list());
    }

    @Override
    public TripDay getTripDayByIdAllData(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(TripDay.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("tripSteps", FetchMode.SELECT);
        criteria.setFetchMode("tripSteps.directions", FetchMode.SELECT);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (TripDay)returnSingleOrNull(criteria.list());
    }
}
