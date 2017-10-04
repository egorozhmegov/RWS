package service.interfaces;

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
}
