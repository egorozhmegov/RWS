package dao.implementation;

import dao.interfaces.TrainDao;
import model.RailWayStation;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    /**
     * Get root points by id.
     *
     * @param id train id.
     * @return RailWayStation.
     */
    @Override
    public List<RailWayStation> getRootPointsById(int id) {
        Train train = read(id);
        LOG.info(String.format("RootPoint list of train with id = %s loaded seccessfully", id));
        return train.getRootPoints();
    }
}
