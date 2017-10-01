package dao.interfaces;

import model.Passenger;
import java.util.List;

/*
Passenger dao interface. Extends generic interface.
 */
public interface PassengerDao extends GenericDao<Passenger> {
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
