package repository.interfaces;

import domain.trips.*;
import domain.useraccounts.UserAccount;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public interface ITripsManagementRepository {

    public void saveTrip(Trip trip);
    public void saveTripDay(TripDay tripDay);
    public void saveTripDayLocation(TripDayLocation tripDayLocation);
    public void saveTripDirection(TripDirection tripDirection);
    public void saveTripStep(TripStep tripStep);

    public Trip getUserTripById(UserAccount userAccount, Long id);
    public Trip getUserTripByIdAllData(UserAccount userAccount, Long id);
    public List<Trip> getAllUsersTrips(UserAccount userAccount);

    public Trip getTripById(Long id);
    public Trip getTripByIdAllData(Long id);
    public List<Trip> getAllTrips();

    public TripDay getTripDayById(Long id);
    public TripDay getTripDayByIdAllData(Long id);

}
