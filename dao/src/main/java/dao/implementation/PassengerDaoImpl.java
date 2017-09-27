package dao.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 Passenger dao implementation.
 */
public class PassengerDaoImpl extends GenericDaoImpl<Passenger> implements PassengerDao {
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    public PassengerDaoImpl() {
        super(Passenger.class);
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public PassengerDaoImpl(Class<Passenger> genericClass) {
        super(genericClass);
    }
}
