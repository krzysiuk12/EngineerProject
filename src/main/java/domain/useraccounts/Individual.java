package domain.useraccounts;

import domain.common.implementation.BaseObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "individuals")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Individual extends BaseObject {

    public enum Gender {
        MALE,
        FEMALE
    }

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private String description;
    private String facebookAccountUrl;
    private String city;
    private String country;

    public Individual() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Individuals", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Individuals", sequenceName = "PK_Sequence_Individuals", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @Basic
    @Column(name = "firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "middleName")
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateOfBirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
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
    @Column(name = "facebookaccounturl")
    public String getFacebookAccountUrl() {
        return facebookAccountUrl;
    }
    public void setFacebookAccountUrl(String facebookAccountUrl) {
        this.facebookAccountUrl = facebookAccountUrl;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
