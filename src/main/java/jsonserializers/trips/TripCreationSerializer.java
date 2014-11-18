package jsonserializers.trips;

import java.util.Date;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public class TripCreationSerializer {

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<TripDayCreationSerializer> tripDays;

    public TripCreationSerializer() {
    }

    public TripCreationSerializer(String name, String description, Date startDate, Date endDate, List<TripDayCreationSerializer> tripDays) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripDays = tripDays;
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

    public List<TripDayCreationSerializer> getTripDays() {
        return tripDays;
    }

    public void setTripDays(List<TripDayCreationSerializer> tripDays) {
        this.tripDays = tripDays;
    }
}
