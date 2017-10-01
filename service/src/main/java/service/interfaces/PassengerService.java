package service.interfaces;

import model.Passenger;

import java.time.LocalDate;
import java.util.List;

/**
 * Passenger service.
 */
public interface PassengerService extends GenericService<Passenger> {
    /**
     * Get list of registered passenger on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate String
     * @return List<Passenger>
     */
    List<Passenger> getRegisteredPassenger(long trainId,
                                           long departStationId,
                                           long arriveStationId,
                                           String departDate);
}
