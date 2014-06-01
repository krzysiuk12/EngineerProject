package domain.common.interfaces;

import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-05-31.
 */
public interface IUserVersionedBaseObject extends IVersionedBaseObject {

    public UserAccount getCreatedByAccount();
    public void setCreatedByAccount(UserAccount createdByAccount);

    public UserAccount getLastModificationByAccount();
    public void setLastModificationByAccount(UserAccount lastModificationByAccount);

    public UserAccount getRemovedByAccount();
    public void setRemovedByAccount(UserAccount removedByAccount);

}
