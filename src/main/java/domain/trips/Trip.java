package domain.trips;

import domain.useraccounts.UserAccount;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class Trip {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccount getAuthor() {
        return author;
    }

    public void setAuthor(UserAccount author) {
        this.author = author;
    }

    public List<TripDay> getDays() {
        return days;
    }

    public void setDays(List<TripDay> days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
