package service.implementation;

import dao.interfaces.RailWayStationDao;
import dao.interfaces.ScheduleDao;
import exception.ClientServiceException;
import model.RailWayStation;
import model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.ClientService;
import java.time.LocalDate;
import java.util.List;

/**
 *Client service implementation.
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private final static Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private RailWayStationDao stationDao;

    @Override
    public List<Schedule> searchTrains(String station1,
                                       String station2,
                                       String date){
        if(station1 == null
                || station1.trim().isEmpty()
                || station2 == null
                || station2.trim().isEmpty()
                || date == null
                || date.trim().isEmpty()){
            LOG.info("Not valid search data.");
            throw new ClientServiceException("Not valid search data.");
        }
        RailWayStation departStation = stationDao.getStationByTitle(station1);
        RailWayStation arriveStation = stationDao.getStationByTitle(station2);
        int day = dayOfWeek(parseDate(date));

        return scheduleDao.searchTrain(departStation.getId(), arriveStation.getId(), day);
    }


    /**
     *Get number of week day.
     *
     * @param date LocalDate
     * @return int
     */
    public int dayOfWeek(LocalDate date){
        return TrainServiceImpl
                .WEEK_DAYS
                .indexOf(date
                        .getDayOfWeek()
                        .toString()
                        .toLowerCase()
                        .substring(0,3)) + 1;
    }

    /**
     * Parse string date to object LocalDate.
     *
     * @param date String
     * @return LocalDate
     */
    public LocalDate parseDate(String date){
        return LocalDate.of(
                Integer.parseInt(date.split("/")[2]),
                Integer.parseInt(date.split("/")[0]),
                Integer.parseInt(date.split("/")[1]));
    }
}
