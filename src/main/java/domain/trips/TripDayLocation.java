package domain.trips;

import domain.common.implementation.VersionedBaseObject;
import domain.locations.Location;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Entity
@Table(name = "TripDayLocations")
public class TripDayLocation extends VersionedBaseObject {

    private TripDay tripDay;
    private Location location;
    private int ordinal;

    public TripDayLocation() {
    }

    public TripDayLocation(TripDay tripDay, Location location, int ordinal) {
        this.tripDay = tripDay;
        this.location = location;
        this.ordinal = ordinal;
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_TripDayLocation", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_TripDayLocation", sequenceName = "PK_Sequence_TripDayLocation", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "id_tripday", foreignKey = @ForeignKey(name = "FK_tripdaylocation_tripday_tripday"), nullable = false)
    public TripDay getTripDay() {
        return tripDay;
    }
    public void setTripDay(TripDay tripDay) {
        this.tripDay = tripDay;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_location", foreignKey = @ForeignKey(name = "FK_tripdaylocation_location_location"), nullable = false)
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Basic
    @Column(name = "ordinal", nullable = false)
    public int getOrdinal() {
        return ordinal;
    }
    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
