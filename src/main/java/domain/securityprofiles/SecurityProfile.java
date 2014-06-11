package domain.securityprofiles;

import domain.common.implementation.BaseObject;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "SecurityProfiles")
public class SecurityProfile extends BaseObject {

    public enum Status {
        DRAFT,
        ACTIVE,
        OFF,
        REMOVED
    }

    private String name;
    private String description;
    private boolean defaultProfile;
    private boolean passwordSecurityProfileTurnedOn;
    private boolean accountSecurityProfileTurnedOn;
    private Status status;
    private PasswordSecurityProfile passwordSecurityProfile;
    private AccountSecurityProfile accountSecurityProfile;

    public SecurityProfile() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_SecurityProfiles", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_SecurityProfiles", sequenceName = "PK_Sequence_SecurityProfiles", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "defaultProfile", nullable = false)
    public boolean isDefaultProfile() {
        return defaultProfile;
    }
    public void setDefaultProfile(boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    @Basic
    @Column(name = "passwordSecurityProfileTurnedOn", nullable = false)
    public boolean isPasswordSecurityProfileTurnedOn() {
        return passwordSecurityProfileTurnedOn;
    }
    public void setPasswordSecurityProfileTurnedOn(boolean passwordSecurityProfileTurnedOn) {
        this.passwordSecurityProfileTurnedOn = passwordSecurityProfileTurnedOn;
    }

    @Basic
    @Column(name = "accountSecurityProfileTurnedOn", nullable = false)
    public boolean isAccountSecurityProfileTurnedOn() {
        return accountSecurityProfileTurnedOn;
    }
    public void setAccountSecurityProfileTurnedOn(boolean accountSecurityProfileTurnedOn) {
        this.accountSecurityProfileTurnedOn = accountSecurityProfileTurnedOn;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "passwordSecurityProfileId", nullable = true)
    public PasswordSecurityProfile getPasswordSecurityProfile() {
        return passwordSecurityProfile;
    }
    public void setPasswordSecurityProfile(PasswordSecurityProfile passwordSecurityProfile) {
        this.passwordSecurityProfile = passwordSecurityProfile;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountSecurityProfileId", nullable = true)
    public AccountSecurityProfile getAccountSecurityProfile() {
        return accountSecurityProfile;
    }
    public void setAccountSecurityProfile(AccountSecurityProfile accountSecurityProfile) {
        this.accountSecurityProfile = accountSecurityProfile;
    }
}
