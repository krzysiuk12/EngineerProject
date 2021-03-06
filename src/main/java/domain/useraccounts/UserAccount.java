package domain.useraccounts;

import domain.common.implementation.VersionedBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "useraccounts")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"versionNumber", "lastModificationDate", "removalDate", "lastModificationByAccount", "removedByAccount", "token", "password", "userGroup"})
public class UserAccount extends VersionedBaseObject {

    public enum Status {
        CREATED,
        ACTIVE,
        LOCKED_OUT,
        TURNED_OFF,
        REMOVED
    }

    private String login;
    private String password;
    private String token;
    private String email;
    private Status status;
    private boolean passwordChangeRequired;
    private Date lastPasswordChangeDate;
    private int invalidSignInAttemptsCounter;
    private int lockoutCounter;
    private Individual individual;
    private UserGroup userGroup;

    public UserAccount() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_UserAccounts", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_UserAccounts", sequenceName = "PK_Sequence_UserAccounts", initialValue = 5, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "passwordChangeRequired", nullable = false)
    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }
    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastpasswordchangedate")
    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }
    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    @Basic
    @Column(name = "invalidsigninattemptscounter", nullable = false)
    public int getInvalidSignInAttemptsCounter() {
        return invalidSignInAttemptsCounter;
    }
    public void setInvalidSignInAttemptsCounter(int invalidSignInAttemptsCounter) {
        this.invalidSignInAttemptsCounter = invalidSignInAttemptsCounter;
    }

    @Basic
    @Column(name = "lockoutcounter", nullable = false)
    public int getLockoutCounter() {
        return lockoutCounter;
    }
    public void setLockoutCounter(int lockoutCounter) {
        this.lockoutCounter = lockoutCounter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_individual", unique = true, nullable = false)
    public Individual getIndividual() {
        return individual;
    }
    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    @Basic
    @Column(name = "token", length = 20)
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usergroup", foreignKey = @ForeignKey(name = "FK_useraccount_usergroup_usergroup"), nullable = true)
    public UserGroup getUserGroup() {
        return userGroup;
    }
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
