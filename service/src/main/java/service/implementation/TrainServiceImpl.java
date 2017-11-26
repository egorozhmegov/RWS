package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TrainDao;
import exception.TrainExistServiceException;
import exception.TrainNumberServiceException;
import exception.TrainRoutePointAddException;
import exception.TrainRoutePointDataException;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
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
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
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
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
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

            if (!sunAndMon && (departIndex < arriveIndex || (departIndex - arriveIndex) > 1)) {
                valid = false; break;
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
    static boolean isValidPeriodDays(String[] departDays, String[] arriveDays) {
        boolean valid = true;
        int departIndex = WEEK_DAYS.indexOf(departDays[0]);
        int arriveIndex = WEEK_DAYS.indexOf(arriveDays[0]);
        boolean sunAndMon = departIndex == 0 && arriveIndex == 6;
        int diff = 0;

        if (sunAndMon) diff = 1;
        else diff = departIndex - arriveIndex;

        for (int i = 0; i < departDays.length; i++) {
            int depart = WEEK_DAYS.indexOf(departDays[i]);
            int arrive = WEEK_DAYS.indexOf(arriveDays[i]);

            if (!(depart == 0 && arrive == 6) && ((depart - arrive) != diff)) {
                valid = false; break;
            }
        }
        return valid;
    }

    /**
     * Checks validity of route point time.
     *
     * @param departDays    String[]
     * @param arriveDays    String[]
     * @param departureTime LocalTime
     * @param arrivalTime   LocalTime
     * @return boolean
     */
    static boolean isValidRoutePointTime(String[] departDays,
                                         String[] arriveDays,
                                         LocalTime departureTime,
                                         LocalTime arrivalTime) {
        boolean valid = true;

        for (int i = 0; i < departDays.length; i++) {
            if (departDays[i].equals(arriveDays[i])
                    && departureTime.isBefore(arrivalTime)) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    /**
     * Checks possibility add route point in current route.
     *
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
     * @return boolean
     */
    static boolean isAddRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {

        if (isValidFirstRoutePoint(
                station, departureTime, arriveDays, arrivalTime, arriveDays)) {
            return isAddFirstRoutePoint(
                    route, station, departureTime, departDays);

        } else if (isValidLastRoutePoint(
                station, departureTime, departDays, arrivalTime, arriveDays)) {
            return isAddLastRoutePoint(
                    route, station, arrivalTime, arriveDays);

        } else {
            return isAddMiddleRoutePoint(
                    route, station, departureTime, departDays, arrivalTime, arriveDays);
        }
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route         List<Schedule>
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @return boolean
     */
    static boolean isAddFirstRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays) {

        boolean isAdd = true;

        if (!route.isEmpty()) {
            Schedule firstPoint = route.get(0);
            String[] nextDepartDays = firstPoint.getDepartPeriod().split(SEPARATOR);
            String[] nextArriveDays = firstPoint.getArrivePeriod().split(SEPARATOR);
            LocalTime nextDepartureTime = firstPoint.getDepartureTime();
            LocalTime nextArrivalTime = firstPoint.getArrivalTime();

            isAdd = !isValidFirstRoutePoint(
                    station, nextDepartureTime, nextDepartDays, nextArrivalTime, nextArriveDays)
                    && isValidPeriodDays(nextArriveDays, departDays)
                    && isValidRoutePoint(station, nextArrivalTime, nextArriveDays, departureTime, departDays)
                    && isValidRoutePointTime(nextArriveDays, departDays, nextArrivalTime, departureTime);
        }
        return isAdd;
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route         List<Schedule>
     * @param station       RailWayStation
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
     * @return boolean
     */
    static boolean isAddLastRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime arrivalTime,
            String[] arriveDays) {

        boolean isAdd = true;

        if (!route.isEmpty()) {
            Schedule lastPoint = route.get(route.size() - 1);
            String[] prevDepartDays = lastPoint.getDepartPeriod().split(SEPARATOR);
            String[] prevArriveDays = lastPoint.getArrivePeriod().split(SEPARATOR);
            LocalTime prevDepartureTime = lastPoint.getDepartureTime();
            LocalTime prevArrivalTime = lastPoint.getArrivalTime();

            isAdd = !isValidLastRoutePoint(
                    station, prevDepartureTime, prevDepartDays, prevArrivalTime, prevArriveDays)
                    && isValidPeriodDays(arriveDays, prevDepartDays)
                    && isValidRoutePoint(station, arrivalTime, arriveDays, prevDepartureTime, prevDepartDays)
                    && isValidRoutePointTime(arriveDays, prevDepartDays, arrivalTime, prevDepartureTime);
        }
        return isAdd;
    }

    /**
     * Checks possibility add last route point in current route.
     *
     * @param route         List<Schedule>
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
     * @return boolean
     */
    static boolean isAddMiddleRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {

        boolean isAdd = true;

        if (!route.isEmpty()) {
            try{
                Schedule prevPoint =
                        getPrevRoutePoint(route, station, departureTime, departDays, arrivalTime, arriveDays);
                Schedule nextPoint =
                        getNextRoutePoint(route, station, departureTime, departDays, arrivalTime, arriveDays);

                if (prevPoint == null)
                    return false;
                RailWayStation prevStation = prevPoint.getStation();
                String[] prevDepartDays = prevPoint.getDepartPeriod().split(SEPARATOR);
                LocalTime prevDepartureTime = prevPoint.getDepartureTime();

                if (nextPoint == null)
                    return isValidRoutePoint(station, arrivalTime, arriveDays, prevDepartureTime, prevDepartDays);

                String[] nextArriveDays = nextPoint.getArrivePeriod().split(SEPARATOR);
                LocalTime nextArrivalTime = nextPoint.getArrivalTime();

                isAdd = isValidRoutePoint(
                        station, arrivalTime, arriveDays, prevDepartureTime, prevDepartDays)
                        && isValidRoutePoint(
                        station, nextArrivalTime, nextArriveDays, departureTime, departDays)
                        && !isValidLastRoutePoint(
                        prevStation, prevDepartureTime, prevDepartDays, arrivalTime, arriveDays);
            } catch (TrainRoutePointAddException e){
                return false;
            }
        }
        return isAdd;
    }

    /**
     * Gets next route point.
     *
     * @param route         List<Schedule>
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
     * @return Schedule
     */
    static Schedule getNextRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {

        if (!isValidRoutePoint(station, arrivalTime, arriveDays, route.get(0).getDepartureTime(),
                route.get(0).getDepartPeriod().split(SEPARATOR))) {
            return null;
        }

        for (Schedule point : route) {
            LocalTime nextArrivalTime = point.getArrivalTime();
            if (nextArrivalTime == null) continue;
            String[] nextArrivalDays = point.getArrivePeriod().split(SEPARATOR);
            LocalTime nextDepartureTime = point.getDepartureTime();

            if (nextDepartureTime != null) {
                String[] nextDepartDays = point.getDepartPeriod().split(SEPARATOR);

                if ((isValidRoutePoint(station, nextArrivalTime, nextArrivalDays, arrivalTime, arriveDays)
                        && isValidRoutePoint(station, departureTime, departDays, nextArrivalTime, nextArrivalDays))
                        || (isValidRoutePoint(station, arrivalTime, arriveDays, nextArrivalTime, nextArrivalDays)
                        && isValidRoutePoint(station, nextDepartureTime, nextDepartDays, departureTime, departDays))) {
                    throw new TrainRoutePointAddException("Incorrect times in new route point");
                }
            }

            if (isValidRoutePoint(station, nextArrivalTime, nextArrivalDays, departureTime, departDays)) {
                return point;
            }
        }
        return null;
    }

    /**
     * Gets prev route point.
     *
     * @param route         List<Schedule>
     * @param station       RailWayStation
     * @param departureTime LocalTime
     * @param departDays    String[]
     * @param arrivalTime   LocalTime
     * @param arriveDays    String[]
     * @return Schedule
     */
    static Schedule getPrevRoutePoint(
            List<Schedule> route,
            RailWayStation station,
            LocalTime departureTime,
            String[] departDays,
            LocalTime arrivalTime,
            String[] arriveDays) {

        Schedule routePoint = null;

        if(route.size() > 1 && !isValidRoutePoint(
                station,
                route.get(route.size() - 1).getArrivalTime(),
                route.get(route.size() - 1).getArrivePeriod().split(SEPARATOR),
                departureTime,
                departDays)
                && route.get(route.size() - 1).getDepartureTime() == null
                && route.get(route.size() - 1).getDepartPeriod() == null){

            return null;
        }

        for (Schedule point : route) {
            LocalTime prevDepartureTime = point.getDepartureTime();
            if (prevDepartureTime == null) continue;
            String[] prevDepartDays = point.getDepartPeriod().split(SEPARATOR);

            LocalTime prevArrivalTime = point.getArrivalTime();

            if (prevArrivalTime != null) {
                String[] prevArriveDays = point.getArrivePeriod().split(SEPARATOR);

                if ((isValidRoutePoint(station, prevDepartureTime, prevDepartDays, arrivalTime, arriveDays)
                        && isValidRoutePoint(station, departureTime, departDays, prevDepartureTime, prevDepartDays))
                        || (isValidRoutePoint(station, arrivalTime, arriveDays, prevArrivalTime, prevArriveDays)
                        && isValidRoutePoint(station, prevDepartureTime, prevDepartDays, departureTime, departDays))) {
                    throw new TrainRoutePointAddException("Incorrect times in new route point");
                }
            }

            if (isValidRoutePoint(station, arrivalTime, arriveDays, prevDepartureTime, prevDepartDays)) {
                routePoint = point;
            }
        }
        return routePoint;
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

        String[] departPeriod = Stream.of(routePoint.getDepartPeriod().split(SEPARATOR))
                .filter(day -> !day.trim().isEmpty())
                .toArray(String[]::new);

        String[] arrivePeriod = Arrays.stream(routePoint.getArrivePeriod().split(SEPARATOR))
                .filter(day -> !day.trim().isEmpty())
                .toArray(String[]::new);

        Train train = routePoint.getTrain();

        if (!isValidAddRoutePointData(station, departureTime, departPeriod, arrivalTime, arrivePeriod)) {
            LOG.error("Invalid add route point data.");
            throw new TrainRoutePointDataException("Invalid add route point data.");
        }

        List<Schedule> route = trainDao.getRoute(train.getId());

        if (isExistRoutePoint(route, station)
                || !isAddRoutePoint(route, station, departureTime, departPeriod, arrivalTime, arrivePeriod)) {
            LOG.error("Invalid add route point: {}.", station);
            throw new TrainRoutePointAddException(String.format("Invalid add route point: %s.", station));
        }

        List<Integer> intListDepartDays = parseToIntTrainPeriod(departPeriod);
        List<Integer> intListArriveDays = parseToIntTrainPeriod(arrivePeriod);
        List<Integer> days = intListArriveDays.isEmpty() ? intListDepartDays : intListArriveDays;

        for (int i = 0; i < days.size(); i++) {
            Schedule schedule = new Schedule();
            schedule.setStation(station);
            schedule.setTrain(train);
            schedule.setDepartureTime(departureTime);
            schedule.setArrivalTime(arrivalTime);
            schedule.setDepartPeriod(routePoint.getDepartPeriod());
            schedule.setArrivePeriod(routePoint.getArrivePeriod());
            if (intListArriveDays.isEmpty()) schedule.setArrivalDay(0);
            else schedule.setArrivalDay(intListArriveDays.get(i));
            if (intListDepartDays.isEmpty()) schedule.setDepartureDay(0);
            else schedule.setDepartureDay(intListDepartDays.get(i));

            scheduleService.create(schedule);
            LOG.info("Created schedule: '{}'", schedule);
        }
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
            if (Objects.equals(routPoint.getStation().getTitle(), station.getTitle())) {
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
    static List<Integer> parseToIntTrainPeriod(String[] period) {
        return Arrays.stream(period)
                .map(day -> WEEK_DAYS.indexOf(day) + 1)
                .collect(Collectors.toList());
    }


    public void setTrainDao(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    GenericDao<Train> getDao() {
        return trainDao;
    }
}
