package messagekeys;

/**
 * Created by Krzysiu on 2014-06-19.
 */
public enum RegistrationValidationMessageKey implements IMessageKey {

    LOGIN_NOT_UNIQUE_ERROR("UserAccount.Login.NotUnique"),
    EMAIL_NOT_UNIQUE_ERROR("UserAccount.Email.NotUnique"),
    TOKEN_NOT_UNIQUE_ERROR("UserAccount.Token.NotUnique");

    private String messageKey;

    private RegistrationValidationMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
