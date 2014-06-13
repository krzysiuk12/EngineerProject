package services.interfaces;

import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-06-13.
 */
public interface IMailSenderService {

    public void sendAccountActivationMessage(UserAccount userAccount);

}
