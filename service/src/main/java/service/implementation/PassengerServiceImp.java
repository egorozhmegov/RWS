package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.PassengerDao;
import model.Passenger;
import model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ClientService;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Passenger service implementation/
 */
@Service("passengerServiceImpl")
public class PassengerServiceImp extends GenericServiceImpl<Passenger> implements PassengerService {

    private final static Logger LOG = LoggerFactory.getLogger(PassengerServiceImp.class);

    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RailWayStationService railWayStationService;

    /**
     * Get list of registered passengers on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate LocalDate
     * @return List<Passenger>
     */
    @Transactional
    @Override
    public List<Passenger> getRegisteredPassengers(long trainId,
                                                  long departStationId,
                                                  long arriveStationId,
                                                  LocalDate departDate){
        LOG.info("List of registered passengers loaded.");
        List<Passenger> allTrainPassengers =
                passengerDao.getRegisteredPassengers(trainId, departDate);

        List<Passenger> registeredPassengers = new ArrayList<>();

        List<Schedule> route = clientService.getCurrentRoute(
                trainId, railWayStationService
                         .read(departStationId).
                         getTitle(), railWayStationService
                                     .read(arriveStationId)
                                     .getTitle());

        for(Passenger passenger: allTrainPassengers){
            for(Schedule routePoint: route){
                if(Objects.equals(passenger.getStation(), routePoint.getStation())){
                    registeredPassengers.add(passenger);
                    break;
                }
            }
        }

        return registeredPassengers;
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
        LOG.info("Registered passenger loaded.");
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
