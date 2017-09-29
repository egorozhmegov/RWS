package dao.interfaces;

import model.Schedule;
import model.Train;

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
    public List<Train> searchTrain(long departStationId, long arriveStationId, int departDay);
}
