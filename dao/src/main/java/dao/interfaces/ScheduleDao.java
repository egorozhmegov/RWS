package dao.interfaces;

import model.Schedule;

import java.util.List;

/**
 * Schedule station dao interface. Extends generic interface.
 */
public interface ScheduleDao extends GenericDao<Schedule> {

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


    /**
     * Delete all schedules by train id and station id.
     *
     * @param stationId long.
     * @param trainId long.
     */
    void deleteByStationAndTrainId(long stationId, long trainId);
}
