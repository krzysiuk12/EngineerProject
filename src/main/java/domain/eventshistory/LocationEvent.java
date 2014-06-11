package domain.eventshistory;

import domain.common.implementation.BaseObject;
import domain.locations.Location;
import domain.useraccounts.UserAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-31.
 */
@Entity
@Table(name = "LocationEvents")
public class LocationEvent extends BaseObject {

    private enum Type {
        CREATION,
        MODIFICATION,
        REMOVAL
    }

    private Location location;
    private UserAccount userAccount;
    private Date generatedOn;
    private Type type;
    private String description;

    public LocationEvent() {
    }
    public LocationEvent(Long id, Location location, UserAccount userAccount, Type type, String description) {
        super(id);
        this.location = location;
        this.userAccount = userAccount;
        this.type = type;
        this.description = description;
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_LocationEvents", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_LocationEvents", sequenceName = "PK_Sequence_LocationEvents", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "locationid", nullable = false)
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "useraccountid", nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "generatedon", nullable = false)
    public Date getGeneratedOn() {
        return generatedOn;
    }
    public void setGeneratedOn(Date generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
