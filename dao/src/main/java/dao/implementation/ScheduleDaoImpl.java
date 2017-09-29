package dao.implementation;

import dao.interfaces.ScheduleDao;
import model.Employee;
import model.Schedule;
import model.Train;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public List<Train> searchTrain(long departStationId, long arriveStationId, int departDay) {
        String sqlQuery = "SELECT s FROM Schedule AS s";
        Query query = getEntityManager().createQuery(sqlQuery);
        System.out.println(query.getResultList());
        return null;

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
