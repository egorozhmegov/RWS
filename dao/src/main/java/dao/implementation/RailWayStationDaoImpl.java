package dao.implementation;

import dao.interfaces.RailWayStationDao;
import model.RailWayStation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 Railway station dao implementation.
 */
class RailWayStationDaoImpl extends GenericDaoImpl<RailWayStation> implements RailWayStationDao {

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
     * Get RailWayStation by title.
     *
     * @param title String.
     * @return RailWayStation.
     */
    @Override
    public RailWayStation getStationByTitle(String title) {
        try{
            String sqlQuery = String.format("SELECT r FROM RailWayStation AS r WHERE r.title = '%s'", title);
            Query query = getEntityManager().createQuery(sqlQuery);
            RailWayStation station = (RailWayStation) query.getSingleResult();
            return station;
        } catch (Exception e){
            return null;
        }
    }
}
