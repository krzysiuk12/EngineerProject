package domain;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Entity
@Table(name = "Locations")
public class Location {

    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @Id
    @GeneratedValue(generator = "PK_Sequence_Location", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Location", sequenceName = "PK_Sequence_Location", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "latitude")
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
