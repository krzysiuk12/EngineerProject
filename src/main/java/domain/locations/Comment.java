package domain.locations;

import domain.common.implementation.BaseObject;
import domain.useraccounts.UserAccount;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Entity
@Table(name = "Comments")
public class Comment extends BaseObject {

    public enum Rating {
        VERY_BAD(1),
        BAD(2),
        OK(3),
        GOOD(4),
        EXCELLENT(5);

        private int value;

        Rating(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private UserAccount userAccount;
    @JsonBackReference("location-comment")
    private Location location;
    private Rating rating;
    private String comment;
    private Date date;

    public Comment() {
    }

    public Comment(UserAccount userAccount, Location location, Rating rating, String comment, Date date) {
        this.userAccount = userAccount;
        this.location = location;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Comments", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Comments", sequenceName = "PK_Sequence_Comments", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_useraccount", nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_location", nullable = false)
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", length = 50, nullable = false)
    public Rating getRating() {
        return rating;
    }
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
