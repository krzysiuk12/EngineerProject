package services.implementation;

import domain.useraccounts.UserAccount;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.IUserManagementRepository;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public class UserManagementSpringService extends BaseSpringService implements IUserManagementService {

    private IUserManagementRepository userManagementRepository;

    public UserManagementSpringService(IUserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount account) {
        userManagementRepository.saveUserAccount(account);
    }

    @Override
    @Transactional
    public UserAccount getUserAccountById(Long id) {
        return userManagementRepository.getUserAccountById(id);
    }

    @Override
    @Transactional
    public UserAccount getUserAccountByIdAllData(Long id) {
        return userManagementRepository.getUserAccountByIdAllData(id);
    }
}
