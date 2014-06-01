package domain.useraccounts;

import domain.common.implementation.VersionedBaseObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "useraccounts")
public class UserAccount extends VersionedBaseObject {

    public enum Status {
        CREATED,
        ACTIVE,
        LOCKED_OUT,
        OFF,
        REMOVED
    }

    private String login;
    private String password;
    private Status status;
    private boolean passwordChangeRequired;
    private Date lastPasswordChangeDate;
    private Date activatedFrom;
    private Date activatedTo;
    private Date lastSuccessfulSignInDate;
    private Date lastInvalidSignInDate;
    private int invalidSignInAttemptsCounter;
    private Date lockedOutFrom;
    private Date lockedOutTo;
    private int lockoutCounter;
    private Date turnOffDate;
    private Date turnOnDate;
    private Individual individual;

    public UserAccount() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_UserAccounts", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_UserAccounts", sequenceName = "PK_Sequence_UserAccounts", initialValue = 1, allocationSize = 1)
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
    @Column(name = "status")
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

    @Temporal(TemporalType.DATE)
    @Column(name = "lastpasswordchangedate")
    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }
    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lastsuccessfulsignindate")
    public Date getLastSuccessfulSignInDate() {
        return lastSuccessfulSignInDate;
    }
    public void setLastSuccessfulSignInDate(Date lastSuccessfulSignInDate) {
        this.lastSuccessfulSignInDate = lastSuccessfulSignInDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lastinvalidsignindate")
    public Date getLastInvalidSignInDate() {
        return lastInvalidSignInDate;
    }
    public void setLastInvalidSignInDate(Date lastInvalidSignInDate) {
        this.lastInvalidSignInDate = lastInvalidSignInDate;
    }

    @Basic
    @Column(name = "invalidsigninattemptscounter")
    public int getInvalidSignInAttemptsCounter() {
        return invalidSignInAttemptsCounter;
    }
    public void setInvalidSignInAttemptsCounter(int invalidSignInAttemptsCounter) {
        this.invalidSignInAttemptsCounter = invalidSignInAttemptsCounter;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lockedoutfrom")
    public Date getLockedOutFrom() {
        return lockedOutFrom;
    }
    public void setLockedOutFrom(Date lockedOutFrom) {
        this.lockedOutFrom = lockedOutFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lockedoutto")
    public Date getLockedOutTo() {
        return lockedOutTo;
    }
    public void setLockedOutTo(Date lockedOutTo) {
        this.lockedOutTo = lockedOutTo;
    }

    @Basic
    @Column(name = "lockoutcounter")
    public int getLockoutCounter() {
        return lockoutCounter;
    }
    public void setLockoutCounter(int lockoutCounter) {
        this.lockoutCounter = lockoutCounter;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "individualid", unique = true, nullable = false)
    public Individual getIndividual() {
        return individual;
    }
    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "activatedfrom")
    public Date getActivatedFrom() {
        return activatedFrom;
    }
    public void setActivatedFrom(Date activatedFrom) {
        this.activatedFrom = activatedFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "activatedto")
    public Date getActivatedTo() {
        return activatedTo;
    }
    public void setActivatedTo(Date activatedTo) {
        this.activatedTo = activatedTo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "turnoffdate")
    public Date getTurnOffDate() {
        return turnOffDate;
    }
    public void setTurnOffDate(Date turnOffDate) {
        this.turnOffDate = turnOffDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "turnondate")
    public Date getTurnOnDate() {
        return turnOnDate;
    }
    public void setTurnOnDate(Date turnOnDate) {
        this.turnOnDate = turnOnDate;
    }
}
