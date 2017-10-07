package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.ScheduleDao;
import model.Schedule;
import model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ScheduleService;

import java.util.List;

/**
 * Schedule service implementation.
 */
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule> implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    /**
     * Delete schedule by train id.
     *
     * @param trainId long
     */
    @Transactional
    @Override
    public void deleteByTrainId(long trainId){
        scheduleDao.deleteByTrainId(trainId);
    }

    /**
     * Delete schedule by station id.
     *
     * @param stationId long
     */
    @Transactional
    @Override
    public void deleteByStationId(long stationId){
        scheduleDao.deleteByStationId(stationId);
    }

    /**
     * Get list train of a select day, which have in route departure and arrival stations.
     *
     * @param departStationId long
     * @param arriveStationId long
     * @param departDay int
     * @return List<Train>
     */
    @Transactional
    @Override
    public List<Schedule> searchTrain(long departStationId, long arriveStationId, int departDay){
        return scheduleDao.searchTrain(departStationId, arriveStationId, departDay);
    }

    /**
     * Get station departure schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getStationDepartSchedule(long stationId, int weekDay){
        return scheduleDao.getStationDepartSchedule(stationId, weekDay);
    }

    /**
     * Get station arrival schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getStationArriveSchedule(long stationId, int weekDay){
        return scheduleDao.getStationArriveSchedule(stationId, weekDay);
    }

    /**
     * Delete all schedules by train id and station id.
     *
     * @param stationId long.
     * @param trainId long.
     */
    @Transactional
    @Override
    public void deleteByStationAndTrainId(long stationId, long trainId) {
        scheduleDao.deleteByStationAndTrainId(stationId, trainId);
    }

    @Override
    GenericDao<Schedule> getDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }
}
