package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.PassengerDao;
import model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.PassengerService;

import java.time.LocalDate;
import java.util.List;


/**
 * Passenger service implementation/
 */
@Service("passengerServiceImpl")
public class PassengerServiceImp extends GenericServiceImpl<Passenger> implements PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    /**
     * Get list of registered passengers on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate String
     * @return List<Passenger>
     */
    @Transactional
    @Override
    public List<Passenger> getRegisteredPassengers(long trainId,
                                                  long departStationId,
                                                  long arriveStationId,
                                                  String departDate){
        return passengerDao.getRegisteredPassengers(trainId,departStationId,arriveStationId,departDate);
    }

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
    @Transactional
    @Override
    public Passenger getRegisteredPassenger(long trainId,
                                            long departStationId,
                                            long arriveStationId,
                                            String departDate,
                                            Passenger passenger){
        return passengerDao.getRegisteredPassenger(
                trainId
                ,departStationId
                ,arriveStationId
                ,departDate
                ,passenger);
    }

    @Override
    GenericDao<Passenger> getDao() {
        return passengerDao;
    }

    public void setPassengerDao(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }
}
