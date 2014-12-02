package domain.common.implementation;

import domain.common.interfaces.IUserVersionedBaseObject;
import domain.useraccounts.UserAccount;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-31.
 */
@MappedSuperclass
@JsonIgnoreProperties(value = { "lastModificationByAccount", "removedByAccount" })
public abstract class UserVersionedBaseObject extends VersionedBaseObject implements IUserVersionedBaseObject {

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
    @JoinColumn(name = "createdByAccountId")
    public UserAccount getCreatedByAccount() {
        return createdByAccount;
    }
    @Override
    public void setCreatedByAccount(UserAccount createdByAccount) {
        this.createdByAccount = createdByAccount;
    }

    @Override
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lastModificationById")
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

    @Transient
    public void updateInformation(UserAccount executor) {
        if(getId() == null) {
            setCreatedByAccount(executor);
            setCreationDate(new Date());
        }
        setLastModificationByAccount(executor);
        setLastModificationDate(new Date());
    }

}
