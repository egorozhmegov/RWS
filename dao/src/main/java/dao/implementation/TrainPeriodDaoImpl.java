package dao.implementation;

import dao.interfaces.TrainPeriodDao;
import model.TrainPeriod;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Train period dao implementation.
 */
public class TrainPeriodDaoImpl extends GenericDaoImpl<TrainPeriod> implements TrainPeriodDao {
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    public TrainPeriodDaoImpl() {
        super(TrainPeriod.class);
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public TrainPeriodDaoImpl(Class<TrainPeriod> genericClass) {
        super(genericClass);
    }
}
