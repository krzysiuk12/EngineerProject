package services.implementation;

import domain.useraccounts.UserAccount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IUserManagementRepository;
import services.interfaces.IMailSenderService;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Repository
public class UserManagementSpringService implements IUserManagementService {

    private IUserManagementRepository userManagementRepository;
    private IMailSenderService mailSenderService;

    public UserManagementSpringService(IUserManagementRepository userManagementRepository, IMailSenderService mailSenderService) {
        this.userManagementRepository = userManagementRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount account) {
        userManagementRepository.saveUserAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccountById(Long id) {
        UserAccount userAccount = userManagementRepository.getUserAccountById(id);
        mailSenderService.sendAccountActivationMessage(userAccount);
        return userAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccountByIdAllData(Long id) {
        return userManagementRepository.getUserAccountByIdAllData(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUserAccountByToken(String token) {
        return userManagementRepository.authenticateUserAccountByToken(token);
    }
}
