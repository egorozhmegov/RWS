package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.PassengerDao;
import model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.PassengerService;


/**
 * Passenger service implementation/
 */
@Service("passengerServiceImpl")
public class PassengerServiceImp extends GenericServiceImpl<Passenger> implements PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    @Override
    GenericDao<Passenger> getDao() {
        return passengerDao;
    }

    public void setPassengerDao(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }
}
