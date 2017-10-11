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
     * @return List<Passenger>
     */
    List<Passenger> getRegisteredPassengers(long trainId,
                                           LocalDate departDate);


    /**
     * Get registered passenger on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate String
     * @param passenger Passenger
     * @return Passenger
     */
    Passenger getRegisteredPassenger(long trainId,
                                     long departStationId,
                                     long arriveStationId,
                                     String departDate,
                                     Passenger passenger);
}
