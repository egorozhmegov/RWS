package dao.implementation;

import dao.interfaces.ScheduleDao;
import model.Employee;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Schedule dao implementation.
 */
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule> implements ScheduleDao {

    private final static Logger LOG = LoggerFactory.getLogger(ScheduleDaoImpl.class);

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Delete schedule by train id.
     *
     * @param trainId String
     */
    @Override
    public void deleteByTrainId(long trainId) {
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Schedule AS s WHERE s.train.id = '%s'", trainId))
                            .executeUpdate();
    }

    /**
     * Delete schedule by station id.
     *
     * @param stationId String
     */
    @Override
    public void deleteByStationId(long stationId) {
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Schedule AS s WHERE s.station.id = '%s'", stationId))
                            .executeUpdate();
    }

    /**
     * Get list train of a select day, which have in route departure and arrival stations.
     *
     * @param departStationId long
     * @param arriveStationId long
     * @param departDay       int
     * @return List<Train>
     */
    @Override
    public List<Schedule> searchTrain(long departStationId, long arriveStationId, int departDay) {
        String sqlQuery =
                "SELECT s FROM Schedule AS s " +
                        "WHERE s.station.id in (" + departStationId + "," + arriveStationId + ") " +
                        "AND s.train.id IN (" +
                        "SELECT s.train.id FROM Schedule AS s " +
                        "WHERE (s.departureDay = " + departDay + " " +
                        "AND s.station.id = " +  departStationId + ") " +
                        "OR (s.station.id = " + arriveStationId + ") " +
                        "GROUP BY s.train.id HAVING COUNT(s.train.id) > 1 " +
                        "ORDER BY s.departureDay, s.departureTime) " +
                "AND s.departureDay = " + departDay + " " +
                "GROUP BY s.train.id " +
                "HAVING s.station.id = " + departStationId + " " +
                "AND COUNT(s.train.id) > 1";
        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return query.getResultList();
        } catch (Exception e){
            LOG.info("No trains for this request");
            return null;
        }
    }

    /**
     * Get station departure schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getStationDepartSchedule(long stationId, int weekDay){
        String sqlQuery =
                "SELECT s FROM Schedule AS s " +
                "WHERE s.station.id = " + stationId + " " +
                "AND s.departureDay = " + weekDay + " " +
                "ORDER BY s.departureTime";
        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return query.getResultList();
        } catch (Exception e){
            LOG.info("No schedule for this request");
            return null;
        }
    }

    /**
     * Get station arrival schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getStationArriveSchedule(long stationId, int weekDay){
        String sqlQuery =
                "SELECT s FROM Schedule AS s " +
                "WHERE s.station.id = " + stationId + " " +
                "AND s.arrivalDay = " + weekDay + " " +
                "ORDER BY s.arrivalTime";
        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return query.getResultList();
        } catch (Exception e){
            LOG.info("No schedule for this request");
            return null;
        }
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public ScheduleDaoImpl() {
        super(Schedule.class);
    }

    public ScheduleDaoImpl(Class<Schedule> genericClass) {
        super(genericClass);
    }
}
