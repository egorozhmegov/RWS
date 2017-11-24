package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import exception.TrainExistServiceException;
import exception.TrainNumberServiceException;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.PassengerService;
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

    private static final Logger LOG = LoggerFactory.getLogger(TrainServiceImpl.class);

    static final List<String> WEEK_DAYS
            = Arrays.asList("sun", "mon", "tue", "wed", "thu", "fri", "sat");

    private static final String SEPARATOR = ",";

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RailWayStationService railWayStationService;

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private PassengerService passengerService;

    /**
     * Get train route
     *
     * @param trainId long
     * @return List<Schedule>
     */
    @Transactional
    @Override
    public List<Schedule> getRoute(long trainId) {
        LOG.info("Route of train (id = '{}') loaded.", trainId);
        return trainDao.getRoute(trainId);
    }

    /**
     * Get train departure time.
     *
     * @param trainId   long
     * @param stationId long
     * @param weekDay   weekDay
     * @return String
     */
    @Transactional
    @Override
    public String getDepartureTime(long trainId, long stationId, int weekDay) {
        LOG.info("Departure time of train (id = {}) on station (id = {}) loaded.", trainId, stationId);
        return trainDao.getDepartureTime(trainId, stationId, weekDay);
    }

    /**
     * Add train.
     *
     * @param train Train
     */
    @Transactional
    @Override
    public void addTrain(Train train) {
        if (train.getTariff() == 0
                || train.getNumber() == null
                || train.getNumber().trim().isEmpty()) {
            LOG.error("Train number: {} is invalid or tariff = 0.", train.getNumber());
            throw new TrainNumberServiceException(String.format("Train number: %s is invalid.", train.getNumber()));
        } else if (trainDao.getTrainByNumber(train.getNumber()) != null) {
            LOG.error("Train: {} is exist already.", train);
            throw new TrainExistServiceException(String.format("Train: %s is exist already.", train));
        } else {
            trainDao.create(train);
            LOG.info("Created train: '{}'", train);
        }
    }

    /**
     * Remove route point of train.
     *
     * @param routePoint Schedule.
     */
    @Transactional
    @Override
    public void removeRoutePoint(Schedule routePoint) {
        long trainId = routePoint.getTrain().getId();
        long stationId = railWayStationService.getStationByTitle(routePoint
                .getStation()
                .getTitle())
                .getId();
        scheduleService.deleteByStationAndTrainId(stationId, trainId);
        LOG.info("Schedule with train (id = '{}') and station (id = '{}') deleted.", trainId, stationId);
    }

    /**
     * Remove train.
     *
     * @param id long
     */
    @Transactional
    @Override
    public void removeTrain(long id) {
        scheduleService.deleteByTrainId(id);
        LOG.info("All schedules with train (id = '{}') deleted", id);

        passengerService.deleteByTrainId(id);
        LOG.info("All passengers with train (id = '{}') deleted", id);

        trainDao.delete(id);
        LOG.info("Train with (id = '{}') deleted", id);
    }

    /**
     * Check valid data of route point.
     *
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param arrivalTime   LocalTime
     * @param departDays    String[]
     * @param arriveDays    String[]
     * @return boolean
     */
    static boolean isValidAddRoutePointData(
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {
        return isValidRoutePoint(station, departureTime, departDays, arrivalTime, arriveDays)
                || isValidFirstRoutePoint(station, departureTime, departDays, arrivalTime, arriveDays)
                || isValidLastRoutePoint(station, departureTime, departDays, arrivalTime, arriveDays);
    }

    /**
     * Checks validity of first route point.
     *
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isValidFirstRoutePoint(RailWayStation station,
                                          LocalTime departureTime,
                                          String[] departDays,
                                          LocalTime arrivalTime,
                                          String[] arriveDays) {
        return station != null
                && station.getTitle() != null
                && !station.getTitle().isEmpty()
                && departureTime != null
                && departDays.length != 0
                && !departDays[0].trim().isEmpty()
                && arrivalTime == null
                && (arriveDays.length == 0 || arriveDays[0].trim().isEmpty());
    }


    /**
     * Checks validity of last route point.
     *
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isValidLastRoutePoint(RailWayStation station,
                                         LocalTime departureTime,
                                         String[] departDays,
                                         LocalTime arrivalTime,
                                         String[] arriveDays) {
        return station != null
                && station.getTitle() != null
                && !station.getTitle().isEmpty()
                && departureTime == null
                && (departDays.length == 0 || departDays[0].trim().isEmpty())
                && arrivalTime != null
                && arriveDays.length != 0
                && !arriveDays[0].trim().isEmpty();
    }

    /**
     * Checks validity of route point.
     *
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isValidRoutePoint(
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {

        return station != null
                && station.getTitle() != null
                && !station.getTitle().isEmpty()
                && departureTime != null
                && departDays.length != 0
                && !departDays[0].trim().isEmpty()
                && arrivalTime != null
                && arriveDays.length != 0
                && !arriveDays[0].trim().isEmpty()
                && departDays.length == arriveDays.length
                && isValidRoutePointDays(departDays, arriveDays)
                && isValidRoutePointTime(departDays, arriveDays, departureTime, arrivalTime);
    }

    /**
     * Checks validity of route point days.
     *
     * @param departDays String[]
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isValidRoutePointDays(String[] departDays, String[] arriveDays) {
        boolean valid = true;

        for (int i = 0; i < departDays.length; i++) {
            int departIndex = WEEK_DAYS.indexOf(departDays[i]);
            int arriveIndex = WEEK_DAYS.indexOf(arriveDays[i]);
            boolean sunAndMon = departIndex == 0 && arriveIndex == 6;

            if (!sunAndMon) {
                if (departIndex < arriveIndex || (departIndex - arriveIndex) > 1) {
                    valid = false; break;
                }
            }
        }
        return valid && isValidPeriodDays(departDays, arriveDays);
    }

    /**
     * Checks validity of route point days period.
     *
     * @param departDays String[]
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isValidPeriodDays(String[] departDays, String[] arriveDays){
        boolean valid = true;
        int departIndex = WEEK_DAYS.indexOf(departDays[0]);
        int arriveIndex = WEEK_DAYS.indexOf(arriveDays[0]);
        boolean sunAndMon = departIndex == 0 && arriveIndex == 6;
        int diff = 0;

        if(sunAndMon){diff = 1;}
        else {diff = departIndex - arriveIndex;}

        for (int i = 0; i < departDays.length; i++) {
            int depart = WEEK_DAYS.indexOf(departDays[i]);
            int arrive = WEEK_DAYS.indexOf(arriveDays[i]);

            if(!(depart == 0 && arrive == 6)){
                if((depart - arrive) != diff){
                    valid = false; break;
                }
            }
        }
        return  valid;
    }

    /**
     * Checks validity of route point time.
     *
     * @param departDays String[]
     * @param arriveDays String[]
     * @param departureTime LocalTime
     * @param arrivalTime LocalTime
     * @return boolean
     */
    static boolean isValidRoutePointTime(String[] departDays,
                                         String[] arriveDays,
                                         LocalTime departureTime,
                                         LocalTime arrivalTime){
        boolean valid = true;

        for (int i = 0; i < departDays.length; i++) {
            if(departDays[i].equals(arriveDays[i]) && departureTime.isBefore(arrivalTime)){
                valid = false;
                break;
            }
        }
        return valid;
    }

    /**
     * Checks possibility add route point in current route.
     *
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isAddRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays){

            if(isValidFirstRoutePoint(station, departureTime, arriveDays, arrivalTime, arriveDays)){
                return isAddFirstRoutePoint(route, station, departureTime, arriveDays, arrivalTime, arriveDays);

            } else if(isValidFirstRoutePoint(station, departureTime, arriveDays, arrivalTime, arriveDays)){
                return isAddLastRoutePoint(route, station, departureTime, arriveDays, arrivalTime, arriveDays);

            } else {
                return isAddMediumRoutePoint(route, station, departureTime, arriveDays, arrivalTime, arriveDays);
            }
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route List<Schedule>
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isAddFirstRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays){

        boolean isAdd = true;
        if(route.size() > 0) {
            Schedule firstPoint = route.get(0);
            RailWayStation nextStation = firstPoint.getStation();
            String[] nextDepartDays = firstPoint.getDepartPeriod().split(SEPARATOR);
            String[] nextArriveDays = firstPoint.getDepartPeriod().split(SEPARATOR);
            LocalTime nextDepartureTime = firstPoint.getDepartureTime();
            LocalTime nextArrivalTime = firstPoint.getArrivalTime();

            isAdd = !isValidFirstRoutePoint(
                    nextStation, nextDepartureTime, nextDepartDays, nextArrivalTime, nextArriveDays)
                    && isValidPeriodDays(nextArriveDays, departDays)
                    && !isValidFirstRoutePoint(
                            nextStation, nextDepartureTime, nextDepartDays, nextArrivalTime, nextArriveDays)
                    && isValidRoutePointTime(nextArriveDays, departDays, nextArrivalTime, departureTime);
        }
        return isAdd;
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route List<Schedule>
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isAddLastRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays){

        boolean isAdd = true;
        if(route.size() > 0) {
            Schedule lastPoint = route.get(route.size() - 1);
            RailWayStation prevStation = lastPoint.getStation();
            String[] prevDepartDays = lastPoint.getDepartPeriod().split(SEPARATOR);
            String[] prevArriveDays = lastPoint.getDepartPeriod().split(SEPARATOR);
            LocalTime prevDepartureTime = lastPoint.getDepartureTime();
            LocalTime prevArrivalTime = lastPoint.getArrivalTime();

//            isAdd = !isValidFirstRoutePoint(
//                    prevStation, prevDepartureTime, prevDepartDays, prevDepartureTime, prevDepartDays)
//                    && isValidPeriodDays(arriveDays, prevDepartDays)
//                    && !isValidLastRoutePoint(station, departureTime, departDays, arrivalTime, arriveDays)
//                    && isValidRoutePointTime(nextArriveDays, departDays, nextArrivalTime, departureTime);
        }
        return isAdd;
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route List<Schedule>
     * @param station RailWayStation
     * @param departureTime LocalTime
     * @param departDays String[]
     * @param arrivalTime LocalTime
     * @param arriveDays String[]
     * @return boolean
     */
    static boolean isAddMediumRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays){


        return true;
    }

    /**
     * Add route point to train.
     *
     * @param routePoint Schedule
     */
    @Override
    public void addRoutePoint(Schedule routePoint) {

        RailWayStation station = railWayStationService
                .getStationByTitle(routePoint.getStation().getTitle());
        LocalTime departureTime = routePoint.getDepartureTime();
        LocalTime arrivalTime = routePoint.getArrivalTime();
        String[] departPeriod = routePoint.getDepartPeriod().split(SEPARATOR);
        String[] arrivePeriod = routePoint.getArrivePeriod().split(SEPARATOR);

        if (!isValidAddRoutePointData(station, departureTime, departPeriod, arrivalTime, arrivePeriod)){
            LOG.error("Invalid add route point data.");
            throw new TrainNumberServiceException("Invalid add route point data.");
        }

        List<Schedule> route = trainDao.getRoute(routePoint.getTrain().getId());





//        List<Integer> intListDepartDays = parseToIntTrainPeriod(departPeriod);
//        List<Integer> intListArriveDays = parseToIntTrainPeriod(arrivePeriod);
//
//
//
//        List<Integer> days
//                = intListArriveDays.isEmpty()
//                ? intListDepartDays
//                : intListArriveDays;
//
//        for (int i = 0; i < days.size(); i++) {
//            if (arrivePeriod[0].trim().isEmpty()) {
//                for (Integer depDay : intListDepartDays) {
//                    Schedule schedule = new Schedule();
//                    schedule.setStation(station);
//                    schedule.setTrain(train);
//                    schedule.setDepartureTime(departureTime);
//                    schedule.setArrivalTime(arrivalTime);
//                    schedule.setDepartPeriod(departPeriod);
//                    schedule.setArrivePeriod(arrivePeriod);
//                    schedule.setArrivalDay(0);
//                    schedule.setDepartureDay(depDay);
//                    scheduleService.create(schedule);
//                    LOG.info("Created schedule: '{}'", schedule);
//                }
//                break;
//            } else if (departPeriod[0].trim().isEmpty()) {
//                for (Integer arrDay : intListArriveDays) {
//                    Schedule schedule = new Schedule();
//                    schedule.setStation(station);
//                    schedule.setTrain(train);
//                    schedule.setDepartureTime(departureTime);
//                    schedule.setArrivalTime(arrivalTime);
//                    schedule.setDepartPeriod(departPeriod);
//                    schedule.setArrivePeriod(arrivePeriod);
//                    schedule.setArrivalDay(arrDay);
//                    schedule.setDepartureDay(0);
//                    scheduleService.create(schedule);
//                    LOG.info("Created schedule: '{}'", schedule);
//                }
//                break;
//            } else {
//                Schedule schedule = new Schedule();
//                schedule.setStation(station);
//                schedule.setTrain(train);
//                schedule.setDepartureTime(departureTime);
//                schedule.setArrivalTime(arrivalTime);
//                schedule.setDepartPeriod(departPeriod);
//                schedule.setArrivePeriod(arrivePeriod);
//                schedule.setArrivalDay(intListArriveDays.get(i));
//                schedule.setDepartureDay(intListDepartDays.get(i));
//                scheduleService.create(schedule);
//                LOG.info("Created schedule: '{}'", schedule);
//            }
//        }
    }

    /**
     * Find station in route.
     *
     * @param route   List<Schedule>
     * @param station RailWayStation
     * @return boolean
     */
    @Override
    public boolean isExistRoutePoint(List<Schedule> route, RailWayStation station) {
        boolean result = false;
        for (Schedule routPoint : route) {
            if (Objects.equals(routPoint
                    .getStation()
                    .getTitle(), station.getTitle())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Create list of train periods. The days have number format.
     * <p>
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
    @Override
    public List<Integer> parseToIntTrainPeriod(String[] period) {
        List<Integer> result = new ArrayList<>();

        for (String day : period)
            result.add(WEEK_DAYS.indexOf(day) + 1);

        return result;
    }


    /**
     * Check possibility add route point. Added route point must have time between times
     * nearest points or before time the first point or after time the last point.
     *
     * @return boolean
     */
    @Override
    public boolean isPossibleAddRoutePoint(Schedule routePoint, List<Schedule> route) {
        boolean result = true;

        List<Integer> departPeriod
                = parseToIntTrainPeriod(routePoint
                .getDepartPeriod()
                .split(SEPARATOR)
        );

        List<Integer> arrivePeriod
                = parseToIntTrainPeriod(routePoint
                .getArrivePeriod()
                .split(SEPARATOR)
        );

        LocalTime departureTime = routePoint.getDepartureTime();
        LocalTime arrivalTime = routePoint.getArrivalTime();

        if (route.isEmpty()
                && arrivalTime == null
                && departureTime != null
                && arrivePeriod.get(0) == 0
                && departPeriod.get(0) != 0) {
            return true;
        } else if (route.isEmpty()
                && (arrivalTime != null
                || arrivePeriod.get(0) != 0)) {
            return false;
        }


        if (arrivePeriod.get(0) == 0) {
            List<Integer> routeDepartPeriod = parseToIntTrainPeriod(route
                    .get(0)
                    .getDepartPeriod()
                    .split(SEPARATOR));
            LocalTime routeDepartureTime = route.get(0).getDepartureTime();

            for (int i = 0; i < departPeriod.size(); i++) {
                int departureDay = departPeriod.get(i);
                int routeDepartureDay = routeDepartPeriod.get(i);

                if (departureDay > routeDepartureDay
                        && departureDay == 7
                        && routeDepartureDay == 1) {
                    result = true;
                } else if (departureDay > routeDepartureDay) {
                    result = false;
                    break;
                } else if (Objects.equals(departureDay, routeDepartureDay)
                        && departureTime.isAfter(routeDepartureTime)) {
                    result = false;
                    break;
                }
            }
        } else if (departPeriod.get(0) == 0) {

            List<Integer> routeArrivePeriod = parseToIntTrainPeriod(route
                    .get(route.size() - 1)
                    .getArrivePeriod()
                    .split(SEPARATOR));

            LocalTime routeArrivalTime = route.get(route.size() - 1).getArrivalTime();

            for (int i = 0; i < arrivePeriod.size(); i++) {
                int arrivalDay = arrivePeriod.get(i);
                int routeArrivalDay = routeArrivePeriod.get(i);

                if (arrivalDay < routeArrivalDay
                        && arrivalDay == 1
                        && routeArrivalDay == 7) {
                    result = true;
                } else if (arrivalDay < routeArrivalDay) {
                    result = false;
                    break;
                } else if (Objects.equals(arrivalDay, routeArrivalDay)
                        && arrivalTime.isBefore(routeArrivalTime)) {
                    result = false;
                    break;
                }
            }
        } else {
            for (int i = 1; i < route.size(); i++) {
                List<Integer> routeArrivePeriod
                        = parseToIntTrainPeriod(route
                        .get(i)
                        .getArrivePeriod()
                        .split(SEPARATOR));
                LocalTime routeArrivalTime = route.get(i).getArrivalTime();

                List<Integer> prevDepartPeriod
                        = parseToIntTrainPeriod(route
                        .get(i - 1)
                        .getDepartPeriod()
                        .split(SEPARATOR));

                LocalTime prevDepartureTime = route.get(i - 1).getDepartureTime();

                boolean containsFreeRow = true;


                for (int j = 0; j < departPeriod.size(); j++) {
                    int arrivalDay = arrivePeriod.get(j);
                    int departureDay = departPeriod.get(j);
                    int routeArrivalDay = routeArrivePeriod.get(j);
                    int prevDepartureDay = prevDepartPeriod.get(j);

                    if (arrivalDay < prevDepartureDay
                            && arrivalDay == 1
                            && prevDepartureDay == 7
                            && ((arrivalDay < routeArrivalDay
                            || (Objects.equals(arrivalDay, routeArrivalDay)
                            && arrivalTime.isBefore(routeArrivalTime))))
                            && (departureDay < routeArrivalDay
                            || (Objects.equals(departureDay, routeArrivalDay)
                            && departureTime.isBefore(routeArrivalTime)))) {
                        containsFreeRow = true;
                    } else if (arrivalDay < prevDepartureDay) {
                        containsFreeRow = false;
                        break;
                    } else if (Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isBefore(prevDepartureTime)) {
                        containsFreeRow = false;
                        break;
                    } else if (Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isAfter(prevDepartureTime)
                            && arrivalDay > routeArrivalDay) {
                        containsFreeRow = false;
                        break;
                    } else if (Objects.equals(arrivalDay, prevDepartureDay)
                            && arrivalTime.isAfter(prevDepartureTime)
                            && Objects.equals(arrivalDay, routeArrivalDay)
                            && arrivalTime.isAfter(routeArrivalTime)) {
                        containsFreeRow = false;
                        break;
                    } else if (arrivalDay > prevDepartureDay
                            && Objects.equals(arrivalDay, routeArrivalDay)
                            && arrivalTime.isAfter(routeArrivalTime)) {
                        containsFreeRow = false;
                        break;
                    } else if (arrivalDay > routeArrivalDay) {
                        containsFreeRow = false;
                        break;
                    } else if (departureDay > routeArrivalDay) {
                        containsFreeRow = false;
                        break;
                    } else if (Objects.equals(departureDay, routeArrivalDay)
                            && departureTime.isAfter(routeArrivalTime)) {
                        containsFreeRow = false;
                        break;
                    }
                }
                if (containsFreeRow) return true;
            }
            return false;
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
