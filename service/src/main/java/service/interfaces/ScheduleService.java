package service.interfaces;

import model.Schedule;

/**
 * Schedule service.
 */
public interface ScheduleService extends GenericService<Schedule> {

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
