package domain.securityprofiles;

import domain.common.implementation.BaseObject;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "PasswordSecurityProfiles")
public class PasswordSecurityProfile extends BaseObject {

    private int maximumLength;
    private int minimumLength;

    private boolean periodPasswordChangeRequired;
    private int maximumAgeInDays;
    /**
     * Number of days when message should be send to user to inform required password change.
     */
    private int expirationInfoInDays;

    private boolean digitRequired;
    private boolean lowerCaseLetterRequired;
    private boolean upperCaseLetterRequired;
    private boolean specialCharacterRequired;

    public PasswordSecurityProfile() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_PasswordSecurityProfiles", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_PasswordSecurityProfiles", sequenceName = "PK_Sequence_PasswordSecurityProfiles", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "maximumLength")
    public int getMaximumLength() {
        return maximumLength;
    }
    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    @Basic
    @Column(name = "maximumLoginLength")
    public int getMinimumLength() {
        return minimumLength;
    }
    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Basic
    @Column(name = "periodPasswordChangeRequired")
    public boolean isPeriodPasswordChangeRequired() {
        return periodPasswordChangeRequired;
    }
    public void setPeriodPasswordChangeRequired(boolean periodPasswordChangeRequired) {
        this.periodPasswordChangeRequired = periodPasswordChangeRequired;
    }

    @Basic
    @Column(name = "maximumAgeInDays")
    public int getMaximumAgeInDays() {
        return maximumAgeInDays;
    }
    public void setMaximumAgeInDays(int maximumAgeInDays) {
        this.maximumAgeInDays = maximumAgeInDays;
    }

    @Basic
    @Column(name = "expirationInfoInDays")
    public int getExpirationInfoInDays() {
        return expirationInfoInDays;
    }
    public void setExpirationInfoInDays(int expirationInfoInDays) {
        this.expirationInfoInDays = expirationInfoInDays;
    }

    @Basic
    @Column(name = "digitRequired")
    public boolean isDigitRequired() {
        return digitRequired;
    }
    public void setDigitRequired(boolean digitRequired) {
        this.digitRequired = digitRequired;
    }

    @Basic
    @Column(name = "lowerCaseLetterRequired")
    public boolean isLowerCaseLetterRequired() {
        return lowerCaseLetterRequired;
    }
    public void setLowerCaseLetterRequired(boolean lowerCaseLetterRequired) {
        this.lowerCaseLetterRequired = lowerCaseLetterRequired;
    }

    @Basic
    @Column(name = "upperCaseLetterRequired")
    public boolean isUpperCaseLetterRequired() {
        return upperCaseLetterRequired;
    }
    public void setUpperCaseLetterRequired(boolean upperCaseLetterRequired) {
        this.upperCaseLetterRequired = upperCaseLetterRequired;
    }

    @Basic
    @Column(name = "specialCharacterRequired")
    public boolean isSpecialCharacterRequired() {
        return specialCharacterRequired;
    }
    public void setSpecialCharacterRequired(boolean specialCharacterRequired) {
        this.specialCharacterRequired = specialCharacterRequired;
    }
}