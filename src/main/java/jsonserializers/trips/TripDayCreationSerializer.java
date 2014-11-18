package jsonserializers.trips;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public class TripDayCreationSerializer {

    private long originLocationId;
    private long destinationLocationId;
    private List<Long> waypointLocationIds;

    public TripDayCreationSerializer() {
    }

    public TripDayCreationSerializer(long originLocationId, long destinationLocationId, List<Long> waypointLocationIds) {
        this.originLocationId = originLocationId;
        this.destinationLocationId = destinationLocationId;
        this.waypointLocationIds = waypointLocationIds;
    }

    public long getOriginLocationId() {
        return originLocationId;
    }

    public void setOriginLocationId(long originLocationId) {
        this.originLocationId = originLocationId;
    }

    public long getDestinationLocationId() {
        return destinationLocationId;
    }

    public void setDestinationLocationId(long destinationLocationId) {
        this.destinationLocationId = destinationLocationId;
    }

    public List<Long> getWaypointLocationIds() {
        return waypointLocationIds;
    }

    public void setWaypointLocationIds(List<Long> waypointLocationIds) {
        this.waypointLocationIds = waypointLocationIds;
    }
}
