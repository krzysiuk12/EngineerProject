package repository.interfaces;

import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public interface IUserManagementRepository {

    public void saveUserAccount(UserAccount account);

    public UserAccount getUserAccountById(Long id);
    public UserAccount getUserAccountByIdAllData(Long id);

}
