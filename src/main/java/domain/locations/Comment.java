package domain.locations;

import domain.common.implementation.BaseObject;
import domain.useraccounts.UserAccount;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-06-09.
 */
public class Comment extends BaseObject {

    public enum Rating {
        VERY_BAD,
        BAD,
        OK,
        GOOD,
        EXCELLENT
    }

    private UserAccount userAccount;
    private Location location;
    private Rating rating;
    private String comment;

    public Comment() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Comments", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Comments", sequenceName = "PK_Sequence_Comments", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "useraccountid", nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "locationid", nullable = false)
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rating", nullable = false)
    public Rating getRating() {
        return rating;
    }
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "message")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
