package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import exception.TrainException;
import model.RailWayStation;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.TrainService;
import java.util.Set;

/**
 * Train service implementation.
 */
@Service("trainServiceImpl")
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
    public Set<RailWayStation> getRoutePointById(long id) {
        return trainDao.getRoutePointById(id);
    }

    @Override
    public boolean existTrain(String number) {
        if(number == null || number.isEmpty()){
            throw new TrainException("Null or empty train number.");
        } else {
            boolean exist = false;
            for(Train train : getDao().getAll()){
                if(number.equals(train.getNumber())){
                    exist = true;
                    break;
                }
            }
            LOG.info(String.format("Exist train: %s", exist));
            return exist;
        }
    }

    public void setTrainDao(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    GenericDao<Train> getDao() {
        return trainDao;
    }
}
