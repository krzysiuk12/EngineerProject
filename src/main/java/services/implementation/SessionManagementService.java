package services.implementation;

import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.ICodeGeneratorService;
import services.interfaces.IEventsService;
import services.interfaces.ISessionManagementService;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Service
public class SessionManagementService implements ISessionManagementService {

    private IUserManagementService userManagementService;
    private ICodeGeneratorService codeGeneratorService;
    private IEventsService eventsService;

    @Autowired
    public SessionManagementService(IUserManagementService userManagementService, ICodeGeneratorService codeGeneratorService, IEventsService eventsService) {
        this.userManagementService = userManagementService;
        this.codeGeneratorService = codeGeneratorService;
        this.eventsService = eventsService;
    }

    @Override
    @Transactional
    public String loginUser(String login, String password, String ipAddress, String sessionId) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountByLogin(login);
        try {
            if(userAccount == null) {
                throw new FormValidationError(ErrorMessages.INVALID_LOGIN);
            }
            if(!userAccount.getPassword().equals(password)) {
                throw new FormValidationError(ErrorMessages.INVALID_PASSWORD);
            }
            if(userAccount.getStatus() != UserAccount.Status.ACTIVE) {
                throw new FormValidationError(ErrorMessages.USER_ACCOUNT_NOT_ACTIVE);
            }
            userAccount.setToken(codeGeneratorService.generateSessionToken());
            userManagementService.saveUserAccount(userAccount);
            eventsService.saveSuccessfulLoginEvent(userAccount, sessionId, ipAddress);
            return userAccount.getToken();
        } catch(FormValidationError ex) {
            if(userAccount != null) {
                eventsService.saveFailedLoginEvent(userAccount, sessionId, ipAddress);
                userAccount.setInvalidSignInAttemptsCounter(userAccount.getInvalidSignInAttemptsCounter() + 1);
                //TODO: Test SecurityProfile and lockout account
                userManagementService.saveUserAccount(userAccount);
            }
            throw ex;
        }

    }

    @Override
    @Transactional
    public void logoutUser(String token, String ipAddress, String sessionId) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountByToken(token);
        // userAccount.setToken(null);
        userManagementService.saveUserAccount(userAccount);
        eventsService.saveLogoutEvent(userAccount, sessionId, ipAddress);
    }
}
