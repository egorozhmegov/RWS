package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.ScheduleDao;
import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ScheduleService;

/**
 * Schedule service implementation.
 */
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule> implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    /**
     * Delete schedule by train.
     *
     * @param train String
     */
    @Transactional
    @Override
    public void deleteByTrain(String train){
        scheduleDao.deleteByTrain(train);
    }

    /**
     * Delete schedule by station.
     *
     * @param station String
     */
    @Transactional
    @Override
    public void deleteByStation(String station){
        scheduleDao.deleteByStation(station);
    }

    @Override
    GenericDao<Schedule> getDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }
}
