package services.interfaces;

import domain.eventshistory.LogEvent;
import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
public interface IEventsService {

    public void saveSuccessfulLoginEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception;

    public void saveFailedLoginEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception;

    public void saveLogoutEvent(UserAccount userAccount, String sessionId, String ipAddress) throws Exception;

    public List<LogEvent> getAllLogEvents();

    public List<LogEvent> getLogEventForUserAccount(UserAccount userAccount) throws Exception;

}
