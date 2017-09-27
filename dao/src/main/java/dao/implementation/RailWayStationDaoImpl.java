package dao.implementation;

import dao.interfaces.RailWayStationDao;
import model.RailWayStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 Railway station dao implementation.
 */
class RailWayStationDaoImpl extends GenericDaoImpl<RailWayStation> implements RailWayStationDao {

    private final static Logger LOG = LoggerFactory.getLogger(RailWayStationDaoImpl.class);

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public RailWayStationDaoImpl() {
        super(RailWayStation.class);
    }

    public RailWayStationDaoImpl(Class<RailWayStation> genericClass) {
        super(genericClass);
    }

    /**
     * Get RailWayStation by name.
     *
     * @param name station name.
     * @return RailWayStation.
     */
    @Override
    public RailWayStation getStationByName(String name) {
        try{
            String sqlQuery = String.format("SELECT r FROM RailWayStation AS r WHERE r.title = '%s'", name);
            Query query = getEntityManager().createQuery(sqlQuery);
            RailWayStation station = (RailWayStation) query.getSingleResult();
            LOG.info(String.format("Found station with name: %s.", name));
            return station;
        } catch (Throwable e){
            LOG.info(String.format("No found station with name: %s.", name));
            return null;
        }
    }
}
