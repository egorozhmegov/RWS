package service.interfaces;

import model.Schedule;

import java.util.List;

/**
 *Client service interface.
 */
public interface ClientService {

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param station1 String
     * @param station2 String
     * @param date String
     * @return
     */
    List<Schedule> searchTrains(String station1, String station2, String date);
}
