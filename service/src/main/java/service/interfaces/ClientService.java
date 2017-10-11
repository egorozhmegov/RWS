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
     * @return List<Schedule>
     */
    List<Schedule> searchTrains(String station1, String station2, String date);

    /**
     * Get train route for client request.
     *
     * @param id long
     * @param station1 String
     * @param station2 String
     * @return List<Schedule>
     */
    List<Schedule> getCurrentRoute(long trainId, String station1, String station2);

    /**
     * Get ticket price.
     *
     * @param currentRoute List<Schedule>.
     * @return int.
     */
    int getTicketPrice(List<Schedule> currentRoute);

    /**
     * Get count of free seats in train.
     *
     * @param month String
     * @param day String
     * @param year String
     * @return int
     */
    int getFreeSeats(String month, String day, String year);
}
