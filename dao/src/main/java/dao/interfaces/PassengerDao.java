package dao.interfaces;

import model.Passenger;
import java.time.LocalDate;
import java.util.List;

/*
Passenger dao interface. Extends generic interface.
 */
public interface PassengerDao extends GenericDao<Passenger> {
    /**
     * Get list of registered passengers on train.
     *
     * @param trainId long
     * @param departDate LocalDate
     * @param arriveDate LocalDate
     * @return List<Passenger>
     */
    List<Passenger> getRegisteredPassengers(long trainId, LocalDate departDate, LocalDate arriveDate);


    /**
     * Get registered passenger on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param passenger Passenger
     * @return Passenger
     */
    Passenger getRegisteredPassenger(long trainId,
                                     long departStationId,
                                     long arriveStationId,
                                     Passenger passenger);

    /**
     * Get all passengers.
     *
     * @return List<Passenger>
     */
    List<Passenger> getAllPassengers();

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
