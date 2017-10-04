package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import exception.TrainServiceException;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ScheduleService;
import service.interfaces.TrainService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Train service implementation.
 */
@Service("trainServiceImpl")
public class TrainServiceImpl extends GenericServiceImpl<Train> implements TrainService {

    private final static Logger LOG = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final static List<String> WEEK_DAYS = Arrays.asList("sun", "mon", "tue", "wed", "thu", "fri", "sat");

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TrainDao trainDao;

    /**
     * Get train route
     *
     * @param trainId long
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getRoute(long trainId){
        return trainDao.getRoute(trainId);
    }

    /**
     * Get train departure time.
     *
     * @param trainId long
     * @param stationId long
     * @param weekDay weekDay
     * @return String
     */
    @Transactional
    @Override
    public String getDepartureTime(long trainId, long stationId, int weekDay){
        return trainDao.getDepartureTime(trainId, stationId, weekDay);
    }

    /**
     * Add train.
     *
     * @param train Train
     */
    @Transactional
    @Override
    public void addTrain(Train train){
        Train checkTrain = trainDao.getTrainByNumber(train.getNumber());

        if(checkTrain != null)
            throw new TrainServiceException("Not valid train data");

        trainDao.create(train);
    }

    /**
     * Remove train.
     *
     * @param id long
     */
    @Transactional
    @Override
    public void removeTrain(long id){
        scheduleService.deleteByTrainId(id);
        LOG.info(String.format("All schedules with train id = '%s' removed", id));

        trainDao.delete(id);
        LOG.info(String.format("Train with id = '%s' removed", id));
    }

    /**
     * Add route point to train.
     *
     * @param routePoint Schedule
     */
    @Override
    public void addRoutePoint(Schedule routePoint){
        
    }

    /**
     * Check on validate data of train periods.
     *
     * @param days String[]
     * @return boolean
     */
    private boolean checkWeekDays(String[] days){
        boolean check = true;
        for (String day : days) {
            if (!WEEK_DAYS.contains(day))
                check = false;
        }
        return check;
    }

    public void setTrainDao(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    GenericDao<Train> getDao() {
        return trainDao;
    }
}
