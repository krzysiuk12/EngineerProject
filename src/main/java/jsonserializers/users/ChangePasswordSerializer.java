package jsonserializers.users;

/**
 * Created by Krzysztof Kicinger on 2014-11-23.
 */
public class ChangePasswordSerializer {

    private String currentPassword;
    private String newPassword;

    public ChangePasswordSerializer() {
    }

    public ChangePasswordSerializer(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
