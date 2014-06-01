package domain.useraccounts;

import domain.common.implementation.VersionedBaseObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Entity
@Table(name = "individuals")
public class Individual extends VersionedBaseObject {

    public enum Gender {
        MALE,
        FEMALE
    }

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private String identityCardNumber;
    private String PESEL;
    private Emails emails;
    private Phones phones;
    private Address address;

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
    @Column(name = "firstName")
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
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
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
    @Column(name = "identityCardNumber")
    public String getIdentityCardNumber() {
        return identityCardNumber;
    }
    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    @Basic
    @Column(name = "PESEL")
    public String getPESEL() {
        return PESEL;
    }
    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    @Embedded
    public Emails getEmails() {
        return emails;
    }
    public void setEmails(Emails emails) {
        this.emails = emails;
    }

    @Embedded
    public Phones getPhones() {
        return phones;
    }
    public void setPhones(Phones phones) {
        this.phones = phones;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
