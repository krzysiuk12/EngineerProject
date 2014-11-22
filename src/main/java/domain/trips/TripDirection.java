package domain.trips;

import domain.common.implementation.VersionedBaseObject;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Entity
@Table(name = "TripDirections")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"versionNumber", "creationDate", "lastModificationDate", "removalDate"})
public class TripDirection extends VersionedBaseObject {

    private int ordinal;
    private TripStep tripStep;
    private String distance;
    private String duration;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;
    private String instruction;
    private TravelMode travelMode;

    public TripDirection() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_TripDirection", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_TripDirection", sequenceName = "PK_Sequence_TripDirection", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @Basic
    @Column(name = "ordinal", nullable = false)
    public int getOrdinal() {
        return ordinal;
    }
    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @JsonBackReference("tripstep-tripdirection")
    @ManyToOne
    @JoinColumn(name = "id_tripstep", foreignKey = @ForeignKey(name = "FK_tripdirection_tripstep_tripstep"), nullable = false)
    public TripStep getTripStep() {
        return tripStep;
    }
    public void setTripStep(TripStep tripStep) {
        this.tripStep = tripStep;
    }

    @Basic
    @Column(name = "distance", length = 100, nullable = false)
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "duration", length = 100, nullable = false)
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "startlatitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "startlongitude"))
    })
    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }
    public void setStartCoordinate(Coordinate startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "endlatitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "endlongitude"))
    })
    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }
    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    @Basic
    @Column(name = "instruction", length = 500, nullable = false)
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "travelmode", nullable = false)
    public TravelMode getTravelMode() {
        return travelMode;
    }
    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }
}
