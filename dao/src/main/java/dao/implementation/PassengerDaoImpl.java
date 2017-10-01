package dao.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

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

    /**
     * Get list of registered passenger on train.
     *
     * @param trainId long
     * @param departStationId long
     * @param arriveStationId long
     * @param departDate String
     * @return List<Passenger>
     */
    @Override
    public List<Passenger> getRegisteredPassenger(long trainId,
                                                  long departStationId,
                                                  long arriveStationId,
                                                  String departDate) {

        String sqlQuery =
                "SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = " + trainId +
                " AND p.trainDate = " + departDate;
        Query query = getEntityManager().createQuery(sqlQuery);
        System.out.println(query.getResultList());
        return null;
    }


    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public PassengerDaoImpl(Class<Passenger> genericClass) {
        super(genericClass);
    }
}
