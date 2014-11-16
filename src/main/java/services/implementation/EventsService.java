package services.implementation;

import domain.eventshistory.LogEvent;
import domain.useraccounts.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IEventsRepository;
import services.interfaces.IEventsService;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@Service
public class EventsService implements IEventsService {

    private IEventsRepository eventsRepository;

    @Autowired
    public EventsService(IEventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    @Transactional
    public void saveSuccessfulLoginEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception {
        eventsRepository.saveLogEvent(new LogEvent(userAccount, new Date(), LogEvent.Type.LOGIN_SUCCESSFUL, sessionId, ipAddress));
    }

    @Override
    @Transactional
    public void saveFailedLoginEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception {
        eventsRepository.saveLogEvent(new LogEvent(userAccount, new Date(), LogEvent.Type.LOGIN_FAILED, sessionId, ipAddress));
    }

    @Override
    @Transactional
    public void saveLogoutEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception {
        eventsRepository.saveLogEvent(new LogEvent(userAccount, new Date(), LogEvent.Type.LOGOUT, sessionId, ipAddress));
    }

    @Override
    public List<LogEvent> getAllLogEvents() {
        return eventsRepository.getAllLogEvents();
    }

    @Override
    public List<LogEvent> getLogEventForUserAccount(UserAccount userAccount) throws Exception {
        return eventsRepository.getLogEventForUserAccount(userAccount);
    }
}
