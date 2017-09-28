package dao.implementation;

import dao.interfaces.ScheduleDao;
import model.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 Schedule dao implementation.
 */
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule> implements ScheduleDao{
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Delete schedule by train.
     *
     * @param train String
     */
    @Override
    public void deleteByTrain(String train) {
        getEntityManager()
                .createQuery(String
                        .format("DELETE FROM Schedule AS s WHERE s.train = '%s'", train))
                            .executeUpdate();
    }

    /**
     * Delete schedule by station.
     *
     * @param station String
     */
    @Override
    public void deleteByStation(String station) {

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
