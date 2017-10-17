package service.interfaces;

import model.Passenger;

import java.time.LocalDate;
import java.util.List;

/**
 * Passenger service.
 */
public interface PassengerService extends GenericService<Passenger> {
    /**
     * Get list of registered passengers on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate LocalDate
     * @return List<Passenger>
     */
    List<Passenger> getRegisteredPassengers(long trainId,
                                           long departStationId,
                                           long arriveStationId,
                                           LocalDate departDate);

    /**
     * Get registered passenger on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDay LocalDate
     * @param passenger Passenger
     * @return Passenger
     */
    public Passenger getRegisteredPassenger(long trainId,
                                            long departStationId,
                                            long arriveStationId,
                                            LocalDate departDay,
                                            Passenger passenger);

    /**
     * Delete all passengers by train id.
     *
     * @param trainId long.
     */
    void deleteByTrainId(long trainId);

    /**
     * Delete all passengers by station id.
     *
     * @param stationId long.
     */
    void deleteByStationId(long stationId);
}
