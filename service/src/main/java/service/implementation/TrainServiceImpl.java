package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import exception.TrainServiceException;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.ScheduleService;
import service.interfaces.TrainService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Train service implementation.
 */
@Service("trainServiceImpl")
public class TrainServiceImpl extends GenericServiceImpl<Train> implements TrainService {

    private final static Logger LOG = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final static List<String> WEEK_DAYS = Arrays
            .asList("sun",
                    "mon",
                    "tue",
                    "wed",
                    "thu",
                    "fri",
                    "sat"
            );

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
        long trainId = routePoint.getTrain().getId();

        List<Schedule> route = trainDao.getRoute(trainId);
        RailWayStation station = routePoint.getStation();
        Train train = trainDao.read(trainId);

        LocalTime departureTime = routePoint.getDepartureTime();
        LocalTime arrivalTime = routePoint.getArrivalTime();

        String departPeriod = routePoint.getDepartPeriod();
        String arrivePeriod = routePoint.getArrivePeriod();

        String[] listDepartDays;
        String[] listArriveDays;

        try{
            listDepartDays = departPeriod.split(",");
            listArriveDays = arrivePeriod.split(",");
        } catch (Exception e){
            LOG.info("Invalid train period data.");
            throw new TrainServiceException("Invalid train period data.");
        }

        if(isExistRoutePoint(route, station)
                || station.getTitle() == null
                || station.getTitle().trim().isEmpty()
                || isValidPeriod(listArriveDays)
                || isValidPeriod(listDepartDays)){
            LOG.info("Invalid add route point data.");
            throw new TrainServiceException("Invalid add route point data.");
        }

        List<Integer> intListDepartDays = parseToIntTrainPeriod(listDepartDays);
        List<Integer> intListArriveDays = parseToIntTrainPeriod(listArriveDays);

        if(!isValidArriveAndDepartDays(
                arrivalTime,
                departureTime,
                intListArriveDays,
                intListDepartDays)){
            LOG.info("Departure time is before arrival time.");
            throw new TrainServiceException("Departure time is before arrival time.");
        }


    }

    /**
     * Find station by title in route.
     *
     * @param route List<Schedule>
     * @param station RailWayStation
     * @return boolean
     */
    private boolean isExistRoutePoint(List<Schedule> route, RailWayStation station){
        boolean result = false;
        for (Schedule routPoint: route) {
            if(Objects
                    .equals(routPoint
                            .getStation()
                            .getTitle(), station.getTitle())){
                result = true;
            }
        }
        return result;
    }

    /**
     * Check on valid data of arrival and departure periods.
     *
     * @param period String[]
     * @return boolean
     */
    private boolean isValidPeriod(String[] period){
        boolean result = true;
        for(String day: period){
            if(!WEEK_DAYS.contains(day)){
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Create list of train periods. The days have number format.
     *
     * 1 - Sunday
     * 2 - Monday
     * 3 - Tuesday
     * 4 - Wednesday
     * 5 - Thursday
     * 6 - Friday
     * 7 - Saturday
     *
     * @param period String[]
     * @return List<Integer>
     */
    private List<Integer> parseToIntTrainPeriod(String[] period){
        List<Integer> result = new ArrayList<>();
        for(String day: period){
            result.add(WEEK_DAYS.indexOf(day) + 1);
        }
        return result;
    }

    /**
     * Check on valid arrival and departure time in route.
     *
     * @param arrivalTime LocalTime
     * @param departureTime LocalTime
     * @param arrivePeriod List<Integer>
     * @param departPeriod List<Integer>
     * @return boolean
     */
    private boolean isValidArriveAndDepartDays(LocalTime arrivalTime,
                                              LocalTime departureTime,
                                              List<Integer> arrivePeriod,
                                              List<Integer> departPeriod){
        boolean result = true;

        if(arrivePeriod.size() != 0
                && departPeriod.size() != 0
                && arrivePeriod.size() == departPeriod.size()){
            for (int i = 0; i < departPeriod.size(); i++) {
                int arriveDay = arrivePeriod.get(i);
                int departDay = arrivePeriod.get(i);
                if(departDay < arriveDay &&
                        departDay != 1
                        && arriveDay != 7
                        ){
                    result = false;
                    break;
                } else if(departDay == arriveDay
                        && departureTime.isBefore(arrivalTime)){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     *Check on valid arrival and departure time in route.
     *
     * @return boolean
     */
    private boolean isValidAddRoutePoint(LocalTime arrivalTime,
                                               LocalTime departureTime,
                                               List<Integer> arrivePeriod,
                                               List<Integer> departPeriod,
                                               List<Schedule> route){
        boolean result = false;



        return result;
    }

    public void setTrainDao(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    GenericDao<Train> getDao() {
        return trainDao;
    }
}
