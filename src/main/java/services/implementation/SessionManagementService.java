package services.implementation;

import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Service
public class SessionManagementService implements ISessionManagementService {

    private IUserManagementService userManagementService;
    private ICodeGeneratorService codeGeneratorService;
    private IEventsService eventsService;
    private ISecurityProfileManagementService securityProfileManagementService;

    @Autowired
    public SessionManagementService(IUserManagementService userManagementService, ICodeGeneratorService codeGeneratorService, IEventsService eventsService, ISecurityProfileManagementService securityProfileManagementService) {
        this.userManagementService = userManagementService;
        this.codeGeneratorService = codeGeneratorService;
        this.eventsService = eventsService;
        this.securityProfileManagementService = securityProfileManagementService;
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
            userAccount.setInvalidSignInAttemptsCounter(0);
            userAccount.setToken(codeGeneratorService.generateSessionToken());
            userManagementService.saveUserAccount(userAccount);
            eventsService.saveSuccessfulLoginEvent(userAccount, sessionId, ipAddress);
            return userAccount.getToken();
        } catch(FormValidationError ex) {
            if(userAccount != null) {
                eventsService.saveFailedLoginEvent(userAccount, sessionId, ipAddress);
                userAccount.setInvalidSignInAttemptsCounter(userAccount.getInvalidSignInAttemptsCounter() + 1);

                SecurityProfile securityProfile = securityProfileManagementService.getDefaultSecurityProfile();
                if(securityProfile != null && userAccount.getInvalidSignInAttemptsCounter() >= securityProfile.getAccountSecurityProfile().getMaximumInvalidLogInAttempts()) {
                    userAccount.setInvalidSignInAttemptsCounter(0);
                    userAccount.setLockoutCounter(userAccount.getLockoutCounter() + 1);
                    if(userAccount.getLockoutCounter() >= securityProfile.getAccountSecurityProfile().getMaximumLockoutsBeforeTurningOff()) {
                        userAccount.setStatus(UserAccount.Status.TURNED_OFF);
                    } else {
                        userAccount.setStatus(UserAccount.Status.LOCKED_OUT);
                        ex.getErrorMessages().clear();
                        ex.getErrorMessages().add(ErrorMessages.USER_ACCOUNT_LOCKED_OUT);
                    }
                }

                userManagementService.saveUserAccount(userAccount);
            }
            throw ex;
        }

    }

    @Override
    @Transactional
    public void logoutUser(String token, String ipAddress, String sessionId) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountByToken(token);
        userAccount.setToken(null);
        userManagementService.saveUserAccount(userAccount);
        eventsService.saveLogoutEvent(userAccount, sessionId, ipAddress);
    }
}
