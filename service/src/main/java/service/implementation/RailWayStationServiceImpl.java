package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.RailWayStationDao;
import exception.RailWayStationNullEntityServiceException;
import model.RailWayStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.RailWayStationService;

/**
 * Railway station service implementation.
 */
public class RailWayStationServiceImpl extends GenericServiceImpl<RailWayStation> implements RailWayStationService {

    private final static Logger LOG = LoggerFactory.getLogger(RailWayStationServiceImpl.class);

    @Autowired
    private RailWayStationDao railWayStationDao;

    /**
     * Gets RailWayStation by name.
     *
     * @param name station name.
     * @return RailWayStation.
     */
    @Override
    @Transactional
    public RailWayStation getStationByName(String name) {
        return railWayStationDao.getStationByName(name);
    }

    @Override
    public boolean existStation(String stationName) {
        if(stationName == null || stationName.isEmpty()){
            throw new RailWayStationNullEntityServiceException("Method: existStation. Null or empty station name.");
        } else {
            boolean exist = false;
            for(RailWayStation station : getDao().getAll()){
                if(stationName.equals(station.getStationName())){
                    exist = true;
                    break;
                }
            }
            return exist;
        }
    }

    @Override
    GenericDao<RailWayStation> getDao() {
        return railWayStationDao;
    }

    public void setRailWayStationDao(RailWayStationDao railWayStationDao) {
        this.railWayStationDao = railWayStationDao;
    }
}
