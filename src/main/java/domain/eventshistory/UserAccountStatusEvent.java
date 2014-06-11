package domain.eventshistory;

import domain.common.implementation.BaseObject;
import domain.useraccounts.UserAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Entity
@Table(name = "UserAccountStatusEvents")
public class UserAccountStatusEvent extends BaseObject {

    private UserAccount userAccount;
    private UserAccount.Status status;
    private Date startDate;
    private Date endDate;


    public UserAccountStatusEvent() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_UserAccountStatusEvents", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_UserAccountStatusEvents", sequenceName = "PK_Sequence_UserAccountStatusEvents", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userAccountId", nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public UserAccount.Status getStatus() {
        return status;
    }
    public void setStatus(UserAccount.Status status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startDate", nullable = false)
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate", nullable = false)
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
