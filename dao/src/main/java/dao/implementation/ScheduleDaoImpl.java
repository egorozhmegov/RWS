package dao.implementation;

import dao.interfaces.ScheduleDao;
import model.Schedule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedule dao implementation.
 */
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule> implements ScheduleDao {

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT = "SELECT s FROM Schedule AS s ";

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
                SELECT +
                        "WHERE s IN " +
                        "(" + SELECT +
                        "WHERE s.station.id IN (:arriveStationId,:departStationId) " +
                        "AND s.train.id IN " +
                        "(SELECT s.train.id FROM Schedule AS s " +
                        "WHERE (s.departureDay = :departDay AND s.station.id = :departStationId) " +
                        "OR s.station.id = :arriveStationId  " +
                        "GROUP BY s.train.id HAVING count(s.train.id) > 1) " +
                        "ORDER BY s.departureDay, s.departureTime) " +
                        "AND s IN (" + SELECT +
                        "WHERE s.departureTime IN" +
                        "(SELECT min(s.departureTime) " +
                        "FROM Schedule AS s " +
                        "WHERE s.departureDay = :departDay " +
                        "AND s.station.id IN (:arriveStationId,:departStationId) " +
                        "GROUP BY s.train.id)) " +
                        "AND s.station.id = :departStationId " +
                        "AND s.departureDay = :departDay";

        Query query = getEntityManager().createQuery(sqlQuery);
        query.setParameter("arriveStationId", arriveStationId);
        query.setParameter("departStationId", departStationId);
        query.setParameter("departDay", departDay);
        try{
            return query.getResultList();
        } catch (Exception e){
            return new ArrayList<>();
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
                SELECT +
                "WHERE s.station.id = :stationId " +
                "AND s.departureDay = :weekDay " +
                "ORDER BY s.departureTime";
        Query query = getEntityManager().createQuery(sqlQuery);
        query.setParameter("stationId", stationId);
        query.setParameter("weekDay", weekDay);
        try{
            return query.getResultList();
        } catch (Exception e){
            return new ArrayList<>();
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
                SELECT +
                "WHERE s.station.id = :stationId " +
                "AND s.arrivalDay = :weekDay " +
                "ORDER BY s.arrivalTime";
        Query query = getEntityManager().createQuery(sqlQuery);
        query.setParameter("stationId", stationId);
        query.setParameter("weekDay", weekDay);
        try{
            return query.getResultList();
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * Delete all schedules by train id and station id.
     *
     * @param stationId long.
     * @param trainId long.
     */
    @Override
    public void deleteByStationAndTrainId(long stationId, long trainId) {
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Schedule AS s " +
                                "WHERE s.station.id = '%s' " +
                                "AND s.train.id = '%s'", stationId, trainId))
                .executeUpdate();
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
