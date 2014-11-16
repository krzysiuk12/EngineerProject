package services.implementation;

import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IUserManagementRepository;
import services.interfaces.IMailSenderService;
import services.interfaces.IUserManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Service
public class UserManagementService implements IUserManagementService {

    private IUserManagementRepository userManagementRepository;
    private IMailSenderService mailSenderService;

    @Autowired
    public UserManagementService(IUserManagementRepository userManagementRepository, IMailSenderService mailSenderService)  {
        this.userManagementRepository = userManagementRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount account) throws Exception {
        userManagementRepository.saveOrUpdateUserAccount(account);
    }

    @Override
    @Transactional
    public void saveIndividual(Individual individual) throws Exception {
        userManagementRepository.saveOrUpdateIndividual(individual);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccountById(Long id) {
        return userManagementRepository.getUserAccountById(id);
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

    private List<ErrorMessages> validateUserAccount(UserAccount userAccount) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(userAccount.getLogin() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_LOGIN);
        }
        if(userAccount.getPassword() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_PASSWORD);
        }
        if(userAccount.getStatus() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_STATUS);
        }
        if(userAccount.getToken() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_TOKEN);
        }
        if(userAccount.getEmail() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_EMAIL);
        }
        return errorMessages;
    }

    private List<ErrorMessages> validateIndividual(Individual individual) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if(individual.getFirstName() == null) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        }
        if(individual.getLastName() == null) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_LAST_NAME);
        }
        return errorMessages;
    }
}
