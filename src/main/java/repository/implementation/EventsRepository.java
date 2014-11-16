package repository.implementation;

import domain.eventshistory.LogEvent;
import domain.useraccounts.UserAccount;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.interfaces.IEventsRepository;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@Repository
public class EventsRepository extends BaseHibernateRepository implements IEventsRepository {

    @Autowired
    public EventsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveLogEvent(LogEvent logEvent) {
        getCurrentSession().saveOrUpdate(logEvent);
    }

    @Override
    public List<LogEvent> getAllLogEvents() {
        return getCurrentSession().createCriteria(LogEvent.class).list();
    }

    @Override
    public List<LogEvent> getLogEventForUserAccount(UserAccount userAccount) throws Exception {
        Criteria criteria = getCurrentSession().createCriteria(LogEvent.class);
        criteria.add(Restrictions.eq("userAccount", userAccount));
        return criteria.list();
    }

}
