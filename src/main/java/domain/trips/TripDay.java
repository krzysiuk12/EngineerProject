package domain.trips;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class TripDay {

    private Trip trip;
    private List<TripDayLocation> locations;
    private List<TripStep> tripSteps;
    private Date date;
    private int dayNumber;
    private Date plannedStartTime;

    public TripDay() {
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<TripDayLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<TripDayLocation> locations) {
        this.locations = locations;
    }

    public List<TripStep> getTripSteps() {
        return tripSteps;
    }

    public void setTripSteps(List<TripStep> tripSteps) {
        this.tripSteps = tripSteps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Date getPlannedStartTime() {
        return plannedStartTime;
    }

    public void setPlannedStartTime(Date plannedStartTime) {
        this.plannedStartTime = plannedStartTime;
    }
}
