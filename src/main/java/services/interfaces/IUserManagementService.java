package services.interfaces;

import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public interface IUserManagementService {

    public void saveUserAccount(UserAccount account) throws Exception;
    public void saveIndividual(Individual individual) throws Exception;
    public void saveUserGroup(UserGroup userGroup) throws Exception;

    public void addUserAccount(String login, String password, String email, String firstName, String lastName) throws Exception;

    public UserAccount getUserAccountById(Long id);
    public UserAccount getUserAccountByIdAllData(Long id);
    public UserAccount getUserAccountByLogin(String login);
    public UserAccount getUserAccountByToken(String token);

    public List<UserAccount> getAllUserAccounts();

    public boolean authenticateUserAccountByToken(String token);
    public boolean authenticateAdminAccountByToken(String token);

    public UserGroup getUserGroupById(Long id);
    public UserGroup getUserGroupByName(String name);
}
