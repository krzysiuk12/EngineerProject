package domain.locations;

import domain.common.implementation.UserVersionedBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Entity
@Table(name = "Locations")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"versionNumber", "lastModificationByAccount", "removedByAccount", "lastModificationDate", "removalDate"})
public class Location extends UserVersionedBaseObject {

    public enum Status {
        /**
         * Draft that user created, cannot be used as Location (FK), can be changed to available state.
         */
        DRAFT,
        /**
         * Currently available and send to user location that will be shown on the map.
         */
        AVAILABLE,
        /**
         * Currently unavaible but not removed from database location. Location is unavailable for some reason for some time.
         */
        UNAVAILABLE,
        /**
         * Permanently unavailable location.
         */
        REMOVED
    }

    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private Status status;
    private boolean usersPrivate;
    private String url;
    private double rating;
    private Address address;

    public Location() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Locations", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Locations", sequenceName = "PK_Sequence_Locations", initialValue = 2, allocationSize = 1)
    public Long getId() {
        return id;
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
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "latitude", nullable = false)
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false)
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "isUsersPrivate")
    public boolean isUsersPrivate() {
        return usersPrivate;
    }
    public void setUsersPrivate(boolean usersPrivate) {
        this.usersPrivate = usersPrivate;
    }

    @Basic
    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
