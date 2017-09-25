package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import model.RailWayStation;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.interfaces.TrainService;
import java.util.List;

/**
 * Train service implementation.
 */
public class TrainServiceImpl extends GenericServiceImpl<Train> implements TrainService {

    private final static Logger LOG = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Autowired
    private TrainDao trainDao;

    /**
     * Get route point by id.
     *
     * @param id train id.
     * @return List<RootPoint>.
     */
    @Override
    public List<RailWayStation> getRoutePointById(int id) {
        return trainDao.getRoutePointById(id);
    }

    public void setTrainDao(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    GenericDao<Train> getDao() {
        return trainDao;
    }
}
