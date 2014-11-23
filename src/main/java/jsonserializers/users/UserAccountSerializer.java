package jsonserializers.users;

/**
 * Created by Krzysztof Kicinger on 2014-11-23.
 */
public class UserAccountSerializer {

    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private String city;
    private String country;

    public UserAccountSerializer() {
    }

    public UserAccountSerializer(String email, String firstName, String lastName, String description, String city, String country) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.city = city;
        this.country = country;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
