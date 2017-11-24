package service.interfaces;

import model.RailWayStation;
import model.Schedule;
import model.Train;

import java.util.List;

/**
 * Train service.
 */
public interface TrainService extends GenericService<Train> {

    /**
     * Get train route
     *
     * @param trainId long
     * @return List<Schedule>
     */
    List<Schedule> getRoute(long trainId);

    /**
     * Get train departure time.
     *
     * @param trainId long
     * @param stationId long
     * @param weekDay weekDay
     * @return String
     */
    String getDepartureTime(long trainId, long stationId, int weekDay);

    /**
     * Add train.
     *
     * @param train Train
     */
    void addTrain(Train train);

    /**
     * Add route point to train.
     *
     * @param routePoint Schedule
     */
    void addRoutePoint(Schedule routePoint);

    /**
     * Remove train.
     *
     * @param id long
     */
    void removeTrain(long id);

    /**
     * Remove route point of train.
     *
     * @param routePoint Schedule.
     */
    void removeRoutePoint(Schedule routePoint);

    /**
     * Find station in route.
     *
     * @param route   List<Schedule>
     * @param station RailWayStation
     * @return boolean
     */
    boolean isExistRoutePoint(List<Schedule> route, RailWayStation station);

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
    List<Integer> parseToIntTrainPeriod(String[] period);

    /**
     * Check possibility add route point. Added route point must have time between times
     * nearest points or before time the first point or after time the last point.
     *
     * @return boolean
     */
    boolean isPossibleAddRoutePoint(Schedule routePoint, List<Schedule> route);
}
