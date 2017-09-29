package dao.implementation;

import dao.interfaces.TrainDao;
import model.RailWayStation;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

/**
 Train dao implementation.
 */
public class TrainDaoImpl extends GenericDaoImpl<Train> implements TrainDao {

    private final static Logger LOG = LoggerFactory.getLogger(TrainDaoImpl.class);

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public TrainDaoImpl() {
        super(Train.class);
    }

    public TrainDaoImpl(Class<Train> genericClass) {
        super(genericClass);
    }

}
