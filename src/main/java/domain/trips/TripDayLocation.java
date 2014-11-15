package domain.trips;

import domain.locations.Location;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class TripDayLocation {

    private TripDay tripDay;
    private Location location;
    private int ordinal;

    public TripDayLocation() {
    }

    public TripDay getTripDay() {
        return tripDay;
    }

    public void setTripDay(TripDay tripDay) {
        this.tripDay = tripDay;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
