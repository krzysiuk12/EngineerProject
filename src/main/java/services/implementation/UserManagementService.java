package services.implementation;

import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IUserManagementRepository;
import services.interfaces.ICodeGeneratorService;
import services.interfaces.IMailSenderService;
import services.interfaces.IUserManagementService;
import tools.ValidationTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Service
public class UserManagementService implements IUserManagementService {

    private ICodeGeneratorService codeGeneratorService;
    private IUserManagementRepository userManagementRepository;
    private IMailSenderService mailSenderService;

    @Autowired
    public UserManagementService(IUserManagementRepository userManagementRepository, IMailSenderService mailSenderService, ICodeGeneratorService codeGeneratorService) {
        this.userManagementRepository = userManagementRepository;
        this.mailSenderService = mailSenderService;
        this.codeGeneratorService = codeGeneratorService;
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount account) throws Exception {
        List<ErrorMessages> errorMessages = validateUserAccount(account);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        userManagementRepository.saveOrUpdateUserAccount(account);
    }

    @Override
    @Transactional
    public void saveIndividual(Individual individual) throws Exception {
        List<ErrorMessages> errorMessages = validateIndividual(individual);
        if (!errorMessages.isEmpty()) {
            throw new FormValidationError(errorMessages);
        }
        userManagementRepository.saveOrUpdateIndividual(individual);
    }

    @Override
    @Transactional
    public void addUserAccount(String login, String password, String email, String firstName, String lastName) throws Exception {
        Individual individual = createIndividual(firstName, lastName);
        saveIndividual(individual);
        UserAccount userAccount = createUserAccount(login, password, email);
        userAccount.setIndividual(individual);
        saveUserAccount(userAccount);
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
        UserAccount account = userManagementRepository.getUserAccountByLogin(login);
        account.setIndividual(null);
        return account;
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
        for (UserAccount account : userAccounts) {
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
        if (userAccount.getLogin() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_LOGIN);
        }
        if (userAccount.getLogin() != null && !ValidationTools.validateLogin(userAccount.getLogin())) {
            errorMessages.add(ErrorMessages.INVALID_USER_LOGIN);
        }
        if (userManagementRepository.validateUniqueLogin(userAccount.getLogin())) {
            errorMessages.add(ErrorMessages.INVALID_USER_LOGIN_EXISTS_IN_SYSTEM);
        }
        if (userAccount.getPassword() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_PASSWORD);
        }
        if (userAccount.getStatus() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_STATUS);
        }
        if (userAccount.getToken() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_TOKEN);
        }
        if (userAccount.getEmail() == null) {
            errorMessages.add(ErrorMessages.INVALID_USER_EMAIL);
        }
        if (userAccount.getEmail() != null && !ValidationTools.validateEmail(userAccount.getEmail())) {
            errorMessages.add(ErrorMessages.INVALID_USER_LOGIN);
        }
        if (userManagementRepository.validateUniqueEmail(userAccount.getEmail())) {
            errorMessages.add(ErrorMessages.INVALID_USER_EMAIL_EXISTS_IN_SYSTEM);
        }
        return errorMessages;
    }

    private List<ErrorMessages> validateIndividual(Individual individual) {
        List<ErrorMessages> errorMessages = new ArrayList<>();
        if (individual.getFirstName() == null) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        }
        if (individual.getFirstName() != null && !ValidationTools.validateFirstName(individual.getFirstName())) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        }
        if (individual.getLastName() == null) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_LAST_NAME);
        }
        if (individual.getLastName() != null && !ValidationTools.validateLastName(individual.getLastName())) {
            errorMessages.add(ErrorMessages.INVALID_INDIVIDUAL_FIRST_NAME);
        }
        return errorMessages;
    }

    private UserAccount createUserAccount(String login, String password, String email) {
        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(login);
        userAccount.setPassword(password);
        userAccount.setEmail(email);
        userAccount.setToken(codeGeneratorService.generateSessionToken());
        userAccount.setStatus(UserAccount.Status.ACTIVE);
        userAccount.setPasswordChangeRequired(false);
        userAccount.setInvalidSignInAttemptsCounter(0);
        userAccount.setLockoutCounter(0);
        return userAccount;
    }

    private Individual createIndividual(String firstName, String lastName) {
        Individual individual = new Individual();
        individual.setFirstName(firstName);
        individual.setLastName(lastName);
        return individual;
    }

}
