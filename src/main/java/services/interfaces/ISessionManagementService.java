package services.interfaces;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
public interface ISessionManagementService {

    public String loginUser(String login, String password, String ipAddress, String sessionId) throws Exception;

    public void logoutUser(String token, String ipAddress, String sessionId) throws Exception;

}
