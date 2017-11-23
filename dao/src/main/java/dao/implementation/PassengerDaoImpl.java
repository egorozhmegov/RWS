package dao.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 Passenger dao implementation.
 */
@SuppressWarnings("EqualsOnSuspiciousObject")
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
     * @param arriveDate LocalDate
     * @return List<Passenger>
     */
    @Override
    public List<Passenger> getRegisteredPassengers(long trainId, LocalDate departDate, LocalDate arriveDate) {
        String sqlQuery =
                String.format("SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = %s " +
                        "AND (p.trainDate BETWEEN '%s' " +
                        "AND '%s')" +
                        "GROUP BY p.birthday, p.passengerFirstName, p.passengerLastName",
                        trainId, Date.valueOf(departDate), Date.valueOf(arriveDate));

        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return query.getResultList();
        } catch(Exception e){
            return new ArrayList<>();
        }
    }


    /**
     * Get registered passenger on train.
     *
     * @param trainId long
     * @param departDate LocalDate
     * @param arriveDate LocalDate
     * @param passenger Passenger
     * @return Passenger
     */
    public Passenger getRegisteredPassenger(long trainId,
                                     LocalDate departDate,
                                     LocalDate arriveDate,
                                     Passenger passenger){
        String sqlQuery =
                String.format("SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = %s " +
                        "AND p.passengerFirstName = '%s' " +
                        "AND p.passengerLastName = '%s' " +
                        "AND p.birthday = '%s' " +
                        "AND (p.trainDate BETWEEN '%s' " +
                        "AND '%s')" +
                        "GROUP BY p.passengerFirstName",
                        trainId,
                        passenger.getPassengerFirstName(),
                        passenger.getPassengerLastName(),
                        Date.valueOf(passenger.getBirthday()),
                        Date.valueOf(departDate),
                        Date.valueOf(arriveDate));

        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return (Passenger) query.getSingleResult();
        } catch(Exception e){
            return null;
        }
    }

    /**
     * Delete all passengers by train id.
     *
     * @param trainId long.
     */
    @Override
    public void deleteByTrainId(long trainId){
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Passenger AS p WHERE p.train.id = '%s'", trainId))
                .executeUpdate();
    }

    /**
     * Delete all passengers by station id.
     *
     * @param stationId long.
     */
    public void deleteByStationId(long stationId){
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Passenger AS p WHERE p.station.id = '%s'", stationId))
                .executeUpdate();
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public PassengerDaoImpl(Class<Passenger> genericClass) {
        super(genericClass);
    }
}
