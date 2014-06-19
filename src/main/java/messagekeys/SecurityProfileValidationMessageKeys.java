package messagekeys;

/**
 * Created by Krzysiu on 2014-06-19.
 */
public enum SecurityProfileValidationMessageKeys implements IMessageKey {

    LOGIN_MAXIMUM_LENGTH_ERROR("AccountSecurityProfile.UserAccount.Login.MaximumLength"),
    LOGIN_MINIMUM_LENGTH_ERROR("AccountSecurityProfile.UserAccount.Login.MinimumLength"),

    MAXIMUM_INVALID_LOGIN_ATTEMPTS_ERROR("AccountSecurityProfile.MaximumInvalidAttempts"),
    MAXIMUM_LOCKOUTS_BEFORE_TURNING_OFF_ERROR("AccountSecurityProfile.MaximumLockoutsBeforeTurningOff"),

    PASSWORD_MAXIMUM_LENGTH_ERROR("PasswordSecurityProfile.UserAccount.Password.MaximumLength"),
    PASSWORD_MINIMUM_LENGTH_ERROR("PasswordSecurityProfile.UserAccount.Password.MinimumLength"),
    PASSWORD_MAXIMUM_AGE_IN_DAYS_ERROR("PasswordSecurityProfile.UserAccount.Password.MaximumAgeInDays"),
    PASSWORD_DIGIT_REQUIRED_ERROR("PasswordSecurityProfile.UserAccount.Password.DigitRequired"),
    PASSWORD_LOWER_CASE_REQUIRED_ERROR("PasswordSecurityProfile.UserAccount.Password.LowerCaseRequired"),
    PASSWORD_UPPER_CASE_REQUIRED_ERROR("PasswordSecurityProfile.UserAccount.Password.UpperCaseRequired"),
    PASSWORD_SPECIAL_CHARACTER_REQUIRED_ERROR("PasswordSecurityProfile.UserAccount.Password.SpecialCharacterRequired");

    private final String messageKey;

    private SecurityProfileValidationMessageKeys(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
