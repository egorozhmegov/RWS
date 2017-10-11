package dao.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;
import util.LocalDateAttributeConverter;

import javax.persistence.Convert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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
     * Get list of registered passengers on train.
     *
     * @param trainId long
     * @param departDate LocalDate
     * @return List<Passenger>
     */
    @Override
    public List<Passenger> getRegisteredPassengers(long trainId,
                                                   LocalDate departDate) {

        String sqlQuery =
                "SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = " + trainId + " " +
                        "AND p.trainDate = '" + Date.valueOf(LocalDate.of(2017, 10, 18)) + "' " +
                        "GROUP BY p.firstName, p.lastName, p.birthday";

        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return query.getResultList();
        } catch(Exception e){
            return null;
        }
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
    public Passenger getRegisteredPassenger(long trainId,
                                     long departStationId,
                                     long arriveStationId,
                                     String departDate,
                                     Passenger passenger){
        String sqlQuery =
                String.format(
                        "SELECT p FROM Passenger AS p " +
                                "WHERE p.train.id = '%s' " +
                                "AND p.trainDate = '%s' " +
                                "AND p.station.id IN('%s','%s') " +
                                "AND p.firstName = '%s' " +
                                "AND p.lastName = '%s' " +
                                "AND p.birthday = '%s' "
                        , trainId
                        , departDate
                        , departStationId
                        , arriveStationId
                        , passenger.getFirstName()
                        , passenger.getLastName()
                        , passenger.getBirthday());
        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return (Passenger) query.getSingleResult();
        } catch(Exception e){
            return null;
        }
    }




    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public PassengerDaoImpl(Class<Passenger> genericClass) {
        super(genericClass);
    }
}
