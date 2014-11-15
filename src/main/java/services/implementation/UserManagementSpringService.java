package services.implementation;

import domain.useraccounts.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IUserManagementRepository;
import services.interfaces.IMailSenderService;
import services.interfaces.IUserManagementService;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Service
public class UserManagementSpringService implements IUserManagementService {

    private IUserManagementRepository userManagementRepository;
    private IMailSenderService mailSenderService;

    @Autowired
    public UserManagementSpringService(IUserManagementRepository userManagementRepository, IMailSenderService mailSenderService) {
        this.userManagementRepository = userManagementRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount account) {
        userManagementRepository.saveOrUpdateUserAccount(account);
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
    public UserAccount getUserAccountByLogin(String login) {
        return userManagementRepository.getUserAccountByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccountByToken(String token) {
        return userManagementRepository.getUserAccountByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> getAllUserAccounts() {
        List<UserAccount> userAccounts = userManagementRepository.getAllUserAccounts();
        for(UserAccount account : userAccounts) {
            account.setIndividual(null);
        }
        return userAccounts;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateUserAccountByToken(String token) {
        UserAccount userAccount = userManagementRepository.getUserAccountByToken(token);
        return userAccount != null && userAccount.getStatus() == UserAccount.Status.ACTIVE;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticateAdminAccountByToken(String token) {
        UserAccount userAccount = userManagementRepository.getUserAccountByToken(token);
        return userAccount != null && userAccount.getStatus() == UserAccount.Status.ACTIVE && userAccount.getLogin().equals("admin");
    }


}
