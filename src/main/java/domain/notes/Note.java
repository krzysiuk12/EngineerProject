package domain.notes;

import domain.common.implementation.BaseObject;
import domain.locations.Location;
import domain.trips.Trip;
import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-05-31.
 */
public class Note extends BaseObject {

    protected String title;
    protected String message;
    protected UserAccount userAccount;
    protected Trip trip;
    protected Location location;

    public Note() {
    }
    public Note(Long id, String title, String message, UserAccount userAccount, Trip trip, Location location) {
        super(id);
        this.title = title;
        this.message = message;
        this.userAccount = userAccount;
        this.trip = trip;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
