package service.interfaces;

import model.Schedule;
import model.Train;

import java.util.List;

/**
 * Schedule service.
 */
public interface ScheduleService extends GenericService<Schedule> {

    /**
     * Delete schedule by train id.
     *
     * @param trainId long
     */
    void deleteByTrainId(long trainId);

    /**
     * Delete schedule by station id.
     *
     * @param stationId long
     */
    void deleteByStationId(long stationId);

    /**
     * Get list train of a select day, which have in route departure and arrival stations.
     *
     * @param departStationId long
     * @param arriveStationId long
     * @param departDay int
     * @return List<Train>
     */
    List<Schedule> searchTrain(long departStationId, long arriveStationId, int departDay);

    /**
     * Get station departure schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    List<Schedule> getStationDepartSchedule(long stationId, int weekDay);

    /**
     * Get station arrival schedule by id.
     *
     * @param stationId long
     * @param weekDay int
     * @return List<Schedule>
     */
    List<Schedule> getStationArriveSchedule(long stationId, int weekDay);
}
