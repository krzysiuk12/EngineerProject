package repository.interfaces;

import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public interface IUserManagementRepository extends IBaseHibernateRepository {

    public UserAccount getUserAccountById(Long id);
    public UserAccount getUserAccountByIdAllData(Long id);
    public UserAccount getUserAccountByLogin(String login);
    public UserAccount getUserAccountByToken(String token);

    public List<UserAccount> getAllUserAccounts();

    public boolean validateUniqueLogin(String login);
    public boolean validateUniqueEmail(String email);

    public UserGroup getUserGroupById(Long id);
    public UserGroup getUserGroupByName(String name);

}
