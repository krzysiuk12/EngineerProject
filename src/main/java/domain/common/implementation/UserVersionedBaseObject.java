package domain.common.implementation;

import domain.common.interfaces.IUserVersionedBaseObject;
import domain.useraccounts.UserAccount;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-31.
 */
public class UserVersionedBaseObject extends VersionedBaseObject implements IUserVersionedBaseObject {

    protected UserAccount createdByAccount;
    protected UserAccount lastModificationByAccount;
    protected UserAccount removedByAccount;

    public UserVersionedBaseObject() {
    }

    public UserVersionedBaseObject(Long id, int versionNumber, Date creationDate, Date lastModificationDate, Date removalDate, UserAccount createdByAccount, UserAccount lastModificationByAccount, UserAccount removedByAccount) {
        super(id, versionNumber, creationDate, lastModificationDate, removalDate);
        this.createdByAccount = createdByAccount;
        this.lastModificationByAccount = lastModificationByAccount;
        this.removedByAccount = removedByAccount;
    }

    @Override
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "createdByAccountId", nullable = false)
    public UserAccount getCreatedByAccount() {
        return createdByAccount;
    }
    @Override
    public void setCreatedByAccount(UserAccount createdByAccount) {
        this.createdByAccount = createdByAccount;
    }

    @Override
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lastModificationById", nullable = false)
    public UserAccount getLastModificationByAccount() {
        return lastModificationByAccount;
    }
    @Override
    public void setLastModificationByAccount(UserAccount lastModificationByAccount) {
        this.lastModificationByAccount = lastModificationByAccount;
    }

    @Override
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "removedById")
    public UserAccount getRemovedByAccount() {
        return removedByAccount;
    }
    @Override
    public void setRemovedByAccount(UserAccount removedByAccount) {
        this.removedByAccount = removedByAccount;
    }
}
