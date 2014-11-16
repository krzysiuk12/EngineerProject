package domain.securityprofiles;

import domain.common.implementation.BaseObject;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "AccountSecurityProfiles")
public class AccountSecurityProfile extends BaseObject {

    private String name;

    private int maximumLoginLength;
    private int minimumLoginLength;

    private int maximumInvalidLogInAttempts;
    private int accountLockOutDurationInMinutes;

    private boolean accountImmediatelyTurnedOff;
    private int maximumLockoutsBeforeTurningOff;

    public AccountSecurityProfile() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_AccountSecurityProfiles", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_AccountSecurityProfiles", sequenceName = "PK_Sequence_AccountSecurityProfiles", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name", length = 200, nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "maximumLoginLength", length = 500, nullable = false)
    public int getMaximumLoginLength() {
        return maximumLoginLength;
    }
    public void setMaximumLoginLength(int maximumLoginLength) {
        this.maximumLoginLength = maximumLoginLength;
    }

    @Basic
    @Column(name = "minimumLoginLength", nullable = false)
    public int getMinimumLoginLength() {
        return minimumLoginLength;
    }
    public void setMinimumLoginLength(int minimumLoginLength) {
        this.minimumLoginLength = minimumLoginLength;
    }

    @Basic
    @Column(name = "maximumInvalidLogInAttempts", nullable = false)
    public int getMaximumInvalidLogInAttempts() {
        return maximumInvalidLogInAttempts;
    }
    public void setMaximumInvalidLogInAttempts(int maximumInvalidLogInAttempts) {
        this.maximumInvalidLogInAttempts = maximumInvalidLogInAttempts;
    }

    @Basic
    @Column(name = "accountLockOutDurationInMinutes", nullable = false)
    public int getAccountLockOutDurationInMinutes() {
        return accountLockOutDurationInMinutes;
    }
    public void setAccountLockOutDurationInMinutes(int accountLockOutDurationInMinutes) {
        this.accountLockOutDurationInMinutes = accountLockOutDurationInMinutes;
    }

    @Basic
    @Column(name = "accountImmediatelyTurnedOff", nullable = false)
    public boolean isAccountImmediatelyTurnedOff() {
        return accountImmediatelyTurnedOff;
    }
    public void setAccountImmediatelyTurnedOff(boolean accountImmediatelyTurnedOff) {
        this.accountImmediatelyTurnedOff = accountImmediatelyTurnedOff;
    }

    @Basic
    @Column(name = "maximumLockoutsBeforeTurningOff", nullable = false)
    public int getMaximumLockoutsBeforeTurningOff() {
        return maximumLockoutsBeforeTurningOff;
    }
    public void setMaximumLockoutsBeforeTurningOff(int maximumLockoutsBeforeTurningOff) {
        this.maximumLockoutsBeforeTurningOff = maximumLockoutsBeforeTurningOff;
    }
}
