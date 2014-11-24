package domain.useraccounts;

import domain.common.implementation.BaseObject;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "individuals")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Individual extends BaseObject {

    private String firstName;
    private String lastName;
    private String description;
    private String city;
    private String country;

    public Individual() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Individuals", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Individuals", sequenceName = "PK_Sequence_Individuals", initialValue = 5, allocationSize = 1)
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
    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
