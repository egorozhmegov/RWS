package service.interfaces;

import model.Schedule;
import util.StationWrapper;

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
     * Get station departure schedule.
     *
     * @param stationWrapper StationWrapper
     * @return List<Schedule>
     */
    List<Schedule> getDepartSchedule(StationWrapper stationWrapper);

    /**
     * Get station arrival schedule.
     *
     * @param stationWrapper StationWrapper
     * @return List<Schedule>
     */
    List<Schedule> getArriveSchedule(StationWrapper stationWrapper);

    /**
     * Delete all schedules by train id and station id.
     *
     * @param stationId long.
     * @param trainId long.
     */
    void deleteByStationAndTrainId(long stationId, long trainId);
}
