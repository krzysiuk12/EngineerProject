package services.interfaces;

import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public interface IUserManagementService {

    public void saveUserAccount(UserAccount account);

    public UserAccount getUserAccountById(Long id);
    public UserAccount getUserAccountByIdAllData(Long id);

    public boolean authenticateUserAccountByToken(String token);
}
