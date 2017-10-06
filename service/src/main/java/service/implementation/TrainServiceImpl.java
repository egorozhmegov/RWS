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
import service.interfaces.RailWayStationService;
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
    private RailWayStationService railWayStationService;

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
        RailWayStation station = railWayStationService
                .getStationByName(routePoint.getStation().getTitle());
        Train train = trainDao.read(trainId);

        LocalTime departureTime = routePoint.getDepartureTime();
        LocalTime arrivalTime = routePoint.getArrivalTime();


        String departPeriod = routePoint.getDepartPeriod();
        String arrivePeriod = routePoint.getArrivePeriod();

        String[] listDepartDays = listDepartDays = departPeriod.split(",");
        String[] listArriveDays = listArriveDays = arrivePeriod.split(",");

        if(station == null
                || isExistRoutePoint(route, station)
                || (listArriveDays[0].trim().isEmpty()
                        && listDepartDays[0].trim().isEmpty())
                || (arrivalTime == null
                        && departureTime == null)){
            LOG.info("Invalid add route point data.");
            throw new TrainServiceException("Invalid add route point data.");
        }

        List<Integer> intListDepartDays = parseToIntTrainPeriod(listDepartDays);
        List<Integer> intListArriveDays = parseToIntTrainPeriod(listArriveDays);

        if(!isValidArriveAndDepartTimes(routePoint)
            && !isPossibleAddRoutePoint(routePoint, route)){
            LOG.info("Departure time is before arrival time.");
            throw new TrainServiceException("Departure time is before arrival time.");
        }

        List<Integer> days = intListArriveDays.size() == 0
                ? intListDepartDays
                : intListArriveDays;

        for(int i = 0; i < days.size(); i++){
            Schedule schedule = new Schedule();
            schedule.setStation(station);
            schedule.setTrain(train);
            schedule.setDepartureTime(departureTime);
            schedule.setArrivalTime(arrivalTime);
            schedule.setDepartPeriod(departPeriod);
            schedule.setArrivePeriod(arrivePeriod);
            if(intListArriveDays.size() == 0){
                for(Integer depDay: intListDepartDays){
                    schedule.setArrivalDay(0);
                    schedule.setDepartureDay(depDay);
                    scheduleService.create(schedule);
                }
            } else if(intListDepartDays.size() == 0){
                for(Integer arrDay: intListArriveDays){
                    schedule.setArrivalDay(arrDay);
                    schedule.setDepartureDay(0);
                    scheduleService.create(schedule);
                }
            } else {
                schedule.setArrivalDay(intListArriveDays.get(i));
                schedule.setDepartureDay(intListDepartDays.get(i));
                scheduleService.create(schedule);
            }
        }
    }

    /**
     * Find station in route.
     *
     * @param route List<Schedule>
     * @param station RailWayStation
     * @return boolean
     */
    public boolean isExistRoutePoint(List<Schedule> route, RailWayStation station){
        boolean result = false;
        for (Schedule routPoint: route) {
            if(Objects.equals(routPoint
                    .getStation()
                    .getTitle(), station.getTitle())){
                result = true;
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
    public List<Integer> parseToIntTrainPeriod(String[] period){
        List<Integer> result = new ArrayList<>();

        for(String day: period)
            result.add(WEEK_DAYS.indexOf(day) + 1);

        return result;
    }

    /**
     * Check on valid arrival and departure time in new route.
     *
     * @param rootPoint Schedule
     * @return boolean
     */
    public boolean isValidArriveAndDepartTimes(Schedule rootPoint){
        boolean result = true;

        LocalTime arrivalTime = rootPoint.getArrivalTime();
        LocalTime departureTime = rootPoint.getDepartureTime();
        List<Integer> arrivePeriod
                = parseToIntTrainPeriod(rootPoint
                .getArrivePeriod()
                .split(","));

        List<Integer> departPeriod
                = parseToIntTrainPeriod(rootPoint
                .getDepartPeriod()
                .split(","));

        if(arrivePeriod.get(0) == 0
                || departPeriod.get(0) == 0)
            return true;

        for (int i = 0; i < departPeriod.size(); i++) {
            int arriveDay = arrivePeriod.get(i);
            int departDay = departPeriod.get(i);

            if(arriveDay > departDay
                    && departDay == 1
                    && arriveDay == 7){
                result = true;
            } else if(arriveDay > departDay){
                result = false; break;
            } else if(departDay == arriveDay
                    && departureTime.isBefore(arrivalTime)){
                result = false; break;
            }
        }
        return result;
    }

    /**
     *Check possibility add route point. Added route point must have time between times
     * nearest points or before time the first point or after time the last point.
     *
     * @return boolean
     */
    public boolean isPossibleAddRoutePoint(Schedule routePoint, List<Schedule> route){
        boolean result = true;

        List<Integer> departPeriod
                = parseToIntTrainPeriod(routePoint
                .getDepartPeriod()
                .split(",")
        );

        List<Integer> arrivePeriod
                = parseToIntTrainPeriod(routePoint
                .getArrivePeriod()
                .split(",")
        );

        LocalTime departureTime = routePoint.getDepartureTime();
        LocalTime arrivalTime = routePoint.getArrivalTime();

        if(route.size() == 0
                && arrivalTime == null
                && departureTime != null
                && arrivePeriod.get(0) == 0
                && departPeriod.get(0) != 0) {
            return true;
        } else if(route.size() == 0
                && (arrivalTime != null
                || arrivePeriod.get(0) != 0)){
            return false;
        }


        if(arrivePeriod.get(0) == 0){
            List<Integer> routeDepartPeriod = parseToIntTrainPeriod(route
                    .get(0)
                    .getDepartPeriod()
                    .split(","));
            LocalTime routeDepartureTime = route.get(0).getDepartureTime();

            for (int i = 0; i < departPeriod.size(); i++) {
                int departureDay = departPeriod.get(i);
                int routeDepartureDay = routeDepartPeriod.get(i);

                if(departureDay > routeDepartureDay){
                    result = false; break;
                } else if(Objects.equals(departureDay, routeDepartureDay)
                        && departureTime.isAfter(routeDepartureTime)){
                    result = false; break;
                }
            }
        } else if (departPeriod.get(0) == 0){

            List<Integer> routeArrivePeriod = parseToIntTrainPeriod(route
                    .get(route.size()-1)
                    .getArrivePeriod()
                    .split(","));

            LocalTime routeArrivalTime = route.get(route.size()-1).getArrivalTime();

            for (int i = 0; i < arrivePeriod.size(); i++) {
                int arrivalDay = arrivePeriod.get(i);
                int routeArrivalDay = routeArrivePeriod.get(i);

                if(arrivalDay < routeArrivalDay){
                    result = false; break;
                } else if(Objects.equals(arrivalDay, routeArrivalDay)
                        && arrivalTime.isBefore(routeArrivalTime)){
                    result = false; break;
                }
            }
        } else {
            for (int i = 1; i < route.size()-2; i++) {
                List<Integer> routeArrivePeriod
                        = parseToIntTrainPeriod(route
                        .get(i)
                        .getArrivePeriod()
                        .split(","));
                LocalTime routeArrivalTime = route.get(i).getArrivalTime();

                List<Integer> routeDepartPeriod
                        = parseToIntTrainPeriod(route
                        .get(i)
                        .getDepartPeriod()
                        .split(","));
                LocalTime routeDepartureTime = route.get(i).getDepartureTime();

                LocalTime prevDepartureTime = route.get(i-1).getDepartureTime();
                int prevDepartureDay = routeDepartPeriod.get(i-1);

                for (int j = 0; j < departPeriod.size(); j++) {
                    int arrivalDay = arrivePeriod.get(j);
                    int departureDay = departPeriod.get(j);
                    int routeArrivalDay = routeArrivePeriod.get(j);

                    if(arrivalDay < prevDepartureDay) {
                        result = false; break;
                    } else if(Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isBefore(prevDepartureTime)){
                        result = false; break;
                    } else if(Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isAfter(prevDepartureTime)
                            && arrivalDay > routeArrivalDay){
                        result = false; break;
                    } else if(Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isAfter(prevDepartureTime)
                            && Objects.equals(arrivalDay, routeArrivalDay)
                            && arrivalTime.isAfter(routeArrivalTime)){
                        result = false; break;
                    } else if(arrivalDay > prevDepartureDay
                            && Objects.equals(arrivalDay, routeArrivalDay)
                            && arrivalTime.isAfter(routeArrivalTime)){
                        result = false; break;
                    } else if(arrivalDay > routeArrivalDay){
                        result = false; break;
                    } else if(departureDay > routeArrivalDay){
                        result = false; break;
                    } else if(Objects.equals(departureDay, routeArrivalDay)
                            && departureTime.isAfter(routeArrivalTime)){
                        result = false; break;
                    }
                }
                if(!result) break;
            }
        }

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
