package services.implementation;

import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.ICodeGeneratorService;
import services.interfaces.ISessionManagementService;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Service
public class SessionManagementSpringService implements ISessionManagementService {

    private IUserManagementService userManagementService;
    private ICodeGeneratorService codeGeneratorService;

    @Autowired
    public SessionManagementSpringService(IUserManagementService userManagementService, ICodeGeneratorService codeGeneratorService) {
        this.userManagementService = userManagementService;
        this.codeGeneratorService = codeGeneratorService;
    }

    @Override
    @Transactional
    public String loginUser(String login, String password) throws Exception {
        UserAccount userAccount = userManagementService.getUserAccountByLogin(login);
        if(userAccount == null) {
            throw new FormValidationError(ErrorMessages.INVALID_LOGIN);
        }
        if(!userAccount.getPassword().equals(password)) {
            throw new FormValidationError(ErrorMessages.INVALID_PASSWORD);
        }
        if(!(userAccount.getStatus() == UserAccount.Status.ACTIVE)) {
            throw new FormValidationError(ErrorMessages.USER_ACCOUNT_NOT_ACTIVE);
        }
        userAccount.setToken(codeGeneratorService.generateSessionToken());
        userManagementService.saveUserAccount(userAccount);
        return userAccount.getToken();
    }

    @Override
    @Transactional
    public void logoutUser(String token) {
        UserAccount userAccount = userManagementService.getUserAccountByToken(token);
        userAccount.setToken(null);
        userManagementService.saveUserAccount(userAccount);
    }
}
