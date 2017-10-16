package dao.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
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
                "SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = " + trainId + " " +
                        "AND (p.trainDate BETWEEN '" + Date.valueOf(departDate) + "' " +
                            "AND '" + Date.valueOf(arriveDate) + "')" +
                        "GROUP BY p.birthday, p.firstName, p.lastName";

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
     * @param passenger Passenger
     * @return Passenger
     */
    public Passenger getRegisteredPassenger(long trainId,
                                     long departStationId,
                                     long arriveStationId,
                                     Passenger passenger){
        String sqlQuery =
                "SELECT p FROM Passenger AS p " +
                        "WHERE p.train.id = " + trainId + " " +
                        "AND p.firstName = '" + passenger.getFirstName() + "' " +
                        "AND p.lastName = '" + passenger.getLastName() + "' " +
                        "AND p.birthday = '" + Date.valueOf(passenger.getBirthday()) + "' " +
                        "AND (p.trainDate BETWEEN " +
                            "(SELECT p.trainDate " +
                            "FROM Passenger AS p " +
                            "WHERE p.train.id = " + trainId + " " +
                            "AND p.station.id = " + departStationId + " " +
                            "GROUP BY p.station.id) " +
                            "AND (SELECT p.trainDate " +
                            "FROM Passenger AS p " +
                            "WHERE p.train.id = " + trainId + " " +
                            "AND p.station.id = " + arriveStationId + " " +
                            "GROUP BY p.station.id)) " +
                        "GROUP BY p.firstName";

        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return (Passenger) query.getSingleResult();
        } catch(Exception e){
            return null;
        }
    }

    /**
     * Get all passengers.
     *
     * @return List<Passenger>
     */
    @Override
    public List<Passenger> getAllPassengers(){
        String sqlQuery =
                "SELECT p FROM Passenger AS p " +
                        "GROUP BY p.firstName, p.lastName, p.birthday";

        Query query = getEntityManager().createQuery(sqlQuery);

        return query.getResultList();
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
