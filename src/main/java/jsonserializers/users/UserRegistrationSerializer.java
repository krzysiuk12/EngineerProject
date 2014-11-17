package jsonserializers.users;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-06-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationSerializer {

    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public UserRegistrationSerializer() {
    }

    public UserRegistrationSerializer(String login, String password, String email, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
