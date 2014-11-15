package services.interfaces;

import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public interface IUserManagementService {

    public void saveUserAccount(UserAccount account);

    public UserAccount getUserAccountById(Long id);
    public UserAccount getUserAccountByIdAllData(Long id);
    public UserAccount getUserAccountByLogin(String login);
    public UserAccount getUserAccountByToken(String token);

    public List<UserAccount> getAllUserAccounts();

    public boolean authenticateUserAccountByToken(String token);
    public boolean authenticateAdminAccountByToken(String token);
}
