package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainPeriodDao;
import model.TrainPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.TrainPeriodService;

/**
 * Train period service implementation.
 */
@Service("trainServiceImpl")
public class TrainPeriodServiceImpl extends GenericServiceImpl<TrainPeriod> implements TrainPeriodService{
    @Autowired
    private TrainPeriodDao trainPeriodDao;

    @Override
    GenericDao<TrainPeriod> getDao() {
        return trainPeriodDao;
    }

    public void setTrainPeriodDao(TrainPeriodDao trainPeriodDao) {
        this.trainPeriodDao = trainPeriodDao;
    }
}
