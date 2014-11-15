package jsonserializers;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginSerializer {

    private String login;
    private String password;
    private String token;
    private List<String> errors;

    public LoginSerializer() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "LoginSerializer{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", errors=" + errors +
                '}';
    }
}
