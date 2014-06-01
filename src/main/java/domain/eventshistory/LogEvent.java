package domain.eventshistory;

import domain.common.implementation.BaseObject;
import domain.useraccounts.UserAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "LogEvents")
public class LogEvent extends BaseObject {

    public enum Type {
        LOGIN_SUCCESSFUL,
        LOGIN_FAILED,
        LOGOUT
    }

    private UserAccount userAccount;
    private Date generatedOn;
    private Type type;
    private String sessionId;
    private String ipAddress;

    public LogEvent() {
    }
    public LogEvent(UserAccount generatedBy, Date generatedOn, Type type, String sessionId, String ipAddress) {
        this.userAccount = generatedBy;
        this.generatedOn = generatedOn;
        this.type = type;
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_LogEvents", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_LogEvents", sequenceName = "PK_Sequence_LogEvents", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userAccountId", nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "generatedOn")
    public Date getGeneratedOn() {
        return generatedOn;
    }
    public void setGeneratedOn(Date generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Basic
    @Column(name = "sessionId")
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
