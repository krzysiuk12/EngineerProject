package domain.trips;

import domain.common.implementation.VersionedBaseObject;
import domain.useraccounts.UserAccount;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@Entity
@Table(name = "Trip")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"versionNumber", "creationDate", "lastModificationDate", "removalDate"})
public class Trip extends VersionedBaseObject {

    private String name;
    private String description;
    private UserAccount author;

    //private List<UserAccount> participants; //TODO: rethink of this functionality

    private List<TripDay> days;
    private Date startDate;
    private Date endDate;

    public Trip() {
    }

    public Trip(String name, String description, UserAccount author, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Trip", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Trip", sequenceName = "PK_Sequence_Trip", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @Basic
    @Column(name = "tripname", length = 100, nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_useraccount", foreignKey = @ForeignKey(name = "FK_trip_useraccount_author"), nullable = false)
    public UserAccount getAuthor() {
        return author;
    }
    public void setAuthor(UserAccount author) {
        this.author = author;
    }

    @JsonManagedReference("trip-tripday")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip")
    public List<TripDay> getDays() {
        return days;
    }
    public void setDays(List<TripDay> days) {
        this.days = days;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "startdate", nullable = false)
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "enddate", nullable = false)
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
