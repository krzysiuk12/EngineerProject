package repository.interfaces;

import domain.eventshistory.LogEvent;
import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
public interface IEventsRepository {

    public void saveLogEvent(LogEvent logEvent);

    public List<LogEvent> getAllLogEvents();

    public List<LogEvent> getLogEventForUserAccount(UserAccount userAccount) throws Exception;

}
