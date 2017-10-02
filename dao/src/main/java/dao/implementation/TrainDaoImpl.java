package dao.implementation;

import dao.interfaces.TrainDao;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public TrainDaoImpl() {
        super(Train.class);
    }

    public TrainDaoImpl(Class<Train> genericClass) {
        super(genericClass);
    }

    /**
     * Get train route
     *
     * @param trainId long
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getRoute(long trainId){
        String sqlQuery =
                "SELECT s FROM Schedule AS s " +
                        "WHERE s.train.id = " + trainId + " " +
                        "GROUP BY s.station.id " +
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
     * Get train departure time.
     *
     * @param trainId long
     * @param stationId long
     * @param weekDay weekDay
     * @return String
     */
    @Override
    public String getDepartureTime(long trainId, long stationId, int weekDay){
        String sqlQuery =
                "SELECT s.departureTime FROM Schedule AS s " +
                        "WHERE s.train.id = " + trainId + " " +
                        "AND s.station.id = " + stationId + " " +
                        "AND s.departureDay = " + weekDay;
        Query query = getEntityManager().createQuery(sqlQuery);
        try{
            return (String) query.getSingleResult();
        } catch (Exception e){
            LOG.info("No departure time for this request");
            return null;
        }
    }

    /**
     * Get train by number.
     *
     * @param number String
     * @return Train
     */
    @Override
    public Train getTrainByNumber(String number) {
        String sqlQuery = String
                .format("SELECT t FROM Train AS t WHERE t.number = '%s'", number);

        Query query = getEntityManager().createQuery(sqlQuery);

        try{
            return (Train) query.getSingleResult();
        } catch (Exception e){
            LOG.info("No train for this request");
            return null;
        }
    }


    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

}
