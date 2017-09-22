package dao.implementation;

import dao.interfaces.ScheduleDao;
import model.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 Schedule dao implementation.
 */
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule> implements ScheduleDao{
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

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
