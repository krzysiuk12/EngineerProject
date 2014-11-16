package domain.trips;

import domain.common.implementation.VersionedBaseObject;
import domain.locations.Location;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Entity
@Table(name = "TripSteps")
public class TripStep extends VersionedBaseObject {

    private TripDay tripDay;
    private Location startLocation;
    private Location endLocation;
    private String distance;
    private DistanceUnit distanceUnit;
    private String duration;
    private List<TripDirection> directions;

    public TripStep() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_TripStep", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_TripStep", sequenceName = "PK_Sequence_TripStep", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "id_tripday", foreignKey = @ForeignKey(name = "FK_tripstep_tripday_tripday"), nullable = false)
    public TripDay getTripDay() {
        return tripDay;
    }
    public void setTripDay(TripDay tripDay) {
        this.tripDay = tripDay;
    }

    @ManyToOne
    @JoinColumn(name = "id_startlocation", foreignKey = @ForeignKey(name = "FK_tripstep_location_startlocation"), nullable = false)
    public Location getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    @ManyToOne
    @JoinColumn(name = "id_endlocation", foreignKey = @ForeignKey(name = "FK_tripstep_location_endlocation"), nullable = false)
    public Location getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    @Basic
    @Column(name = "distance", length = 100, nullable = false)
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "distanceunit", length = 50, nullable = false)
    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }
    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    @Basic
    @Column(name = "duration", length = 100, nullable = false)
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tripStep")
    public List<TripDirection> getDirections() {
        return directions;
    }
    public void setDirections(List<TripDirection> directions) {
        this.directions = directions;
    }
}
