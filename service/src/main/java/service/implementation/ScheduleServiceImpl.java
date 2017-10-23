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

import java.util.List;

/**
 * Schedule service implementation.
 */
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule> implements ScheduleService {

    private final static Logger LOG = LoggerFactory.getLogger(ScheduleServiceImpl.class);

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
        LOG.info(String.format("Schedule with trains (id = '%s') deleted", trainId));
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
        LOG.info(String.format("Schedule with stations (id = '%s') deleted", stationId));
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
        LOG.info("Schedule by two stations loaded.");
        return scheduleDao.searchTrain(departStationId, arriveStationId, departDay);
    }

    /**
     * Get station departure schedule.
     *
     * @param station String
     * @param date String
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getStationDepartSchedule(String station, String date){
        long stationId = railWayStationService.getStationByTitle(station).getId();
        int weekDay = clientService.dayOfWeek(clientService.parseDate(date));
        LOG.info("Departure station schedule loaded.");
        return scheduleDao.getStationDepartSchedule(stationId, weekDay);
    }

    /**
     * Get station arrival schedule.
     *
     * @param station String
     * @param date String
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getStationArriveSchedule(String station, String date){
        long stationId = railWayStationService.getStationByTitle(station).getId();
        int weekDay = clientService.dayOfWeek(clientService.parseDate(date));
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
        LOG.info(String
                .format("Schedule with stations (id = '%s') and trains (id = '%s') deleted", stationId, trainId));
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
