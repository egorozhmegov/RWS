package dao.interfaces;

import model.Schedule;

/**
 * Schedule station dao interface. Extends generic interface.
 */
public interface ScheduleDao extends GenericDao<Schedule> {

    /**
     * Delete schedule by train.
     *
     * @param train String
     */
    void deleteByTrain(String train);

    /**
     * Delete schedule by station.
     *
     * @param station String
     */
    void deleteByStation(String station);
}
