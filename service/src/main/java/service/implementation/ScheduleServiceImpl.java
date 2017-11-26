package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.ScheduleDao;
import model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ClientService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;
import util.StationWrapper;

import java.util.List;

/**
 * Schedule service implementation.
 */
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule> implements ScheduleService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private RailWayStationService railWayStationService;

    @Autowired
    private ClientService clientService;

    /**
     * Delete schedule by train id.
     *
     * @param trainId long
     */
    @Transactional
    @Override
    public void deleteByTrainId(long trainId){
        scheduleDao.deleteByTrainId(trainId);
        LOG.info("Schedule with trains (id = '{}') deleted", trainId);
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
        LOG.info("Schedule with stations (id = '{}') deleted", stationId);
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
        LOG.info("Train from station (id = {}) to station (id = {}) loaded.", departStationId, arriveStationId);
        return scheduleDao.searchTrain(departStationId, arriveStationId, departDay);
    }

    /**
     * Get station departure schedule.
     *
     * @param stationWrapper StationWrapper
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getDepartSchedule(StationWrapper stationWrapper){
        long stationId = railWayStationService.getStationByTitle(stationWrapper.getStation()).getId();
        int weekDay = clientService.dayOfWeek(stationWrapper.getDate());
        LOG.info("Departure station schedule loaded.");
        return scheduleDao.getStationDepartSchedule(stationId, weekDay);
    }

    /**
     * Get station arrival schedule.
     *
     * @param stationWrapper StationWrapper
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getArriveSchedule(StationWrapper stationWrapper){
        long stationId = railWayStationService.getStationByTitle(stationWrapper.getStation()).getId();
        int weekDay = clientService.dayOfWeek(stationWrapper.getDate());
        LOG.info("Arrival station schedule loaded.");
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
        LOG.info("Schedule with stations (id = '{}') and trains (id = '{}') deleted", stationId, trainId);
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
