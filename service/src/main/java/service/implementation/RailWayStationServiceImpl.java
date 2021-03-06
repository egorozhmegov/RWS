package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.RailWayStationDao;
import exception.RailWayStationServiceException;
import model.RailWayStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;

/**
 * Railway station service implementation.
 */
@Service("railWayStationServiceImpl")
public class RailWayStationServiceImpl extends GenericServiceImpl<RailWayStation> implements RailWayStationService {

    private static final Logger LOG = LoggerFactory.getLogger(RailWayStationServiceImpl.class);

    @Autowired
    private RailWayStationDao railWayStationDao;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PassengerService passengerService;

    /**
     * Delete station.
     *
     * @param id long.
     */
    @Transactional
    @Override
    public void removeStation(long id) {
        scheduleService.deleteByStationId(id);
        LOG.info("All schedules with station (id = '{}') deleted", id);

        passengerService.deleteByStationId(id);
        LOG.info("All passengers with station (id = '{}') deleted", id);

        railWayStationDao.delete(id);
        LOG.info("Station with (id = '{}') deleted", id);
    }

    /**
     * Gets RailWayStation by title.
     *
     * @param title String.
     * @return RailWayStation.
     */
    @Transactional
    @Override
    public RailWayStation getStationByTitle(String title) {
        LOG.info("Station '{}' loaded.", title);
        return railWayStationDao.getStationByTitle(title);
    }

    /**
     * Add new station.
     *
     * @param station RailWayStation
     */
    @Transactional
    @Override
    public void addStation(RailWayStation station) {
        if(station == null
                || station.getTitle().trim().isEmpty()
                || isExistStation(station.getTitle())){
            LOG.error("Invalid add train data.");
            throw new RailWayStationServiceException("Invalid add train data.");
        }
        railWayStationDao.create(station);
        LOG.info("Created station '{}'.", station);
    }

    /**
     * Check exist station.
     *
     * @param title String
     * @return boolean
     */
    public boolean isExistStation(String title){
        return railWayStationDao.getStationByTitle(title) != null;
    }

    @Override
    GenericDao<RailWayStation> getDao() {
        return railWayStationDao;
    }

    public void setRailWayStationDao(RailWayStationDao railWayStationDao) {
        this.railWayStationDao = railWayStationDao;
    }
}
