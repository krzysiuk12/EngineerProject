package domain.trips;

import domain.common.implementation.VersionedBaseObject;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Entity
@Table(name = "TripDays")
public class TripDay extends VersionedBaseObject {

    private Trip trip;
    private List<TripDayLocation> locations;
    private List<TripStep> tripSteps;
    private Date date;

    public TripDay() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_TripDay", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_TripDay", sequenceName = "PK_Sequence_TripDay", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "id_trip", foreignKey = @ForeignKey(name = "FK_tripday_trip_trip"), nullable = false)
    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tripDay")
    public List<TripDayLocation> getLocations() {
        return locations;
    }
    public void setLocations(List<TripDayLocation> locations) {
        this.locations = locations;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tripDay")
    public List<TripStep> getTripSteps() {
        return tripSteps;
    }
    public void setTripSteps(List<TripStep> tripSteps) {
        this.tripSteps = tripSteps;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
