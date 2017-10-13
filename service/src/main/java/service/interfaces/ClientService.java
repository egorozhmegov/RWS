package service.interfaces;

import model.Passenger;
import model.Schedule;

import java.util.List;
import java.util.Map;

/**
 * Client service interface.
 */
public interface ClientService {

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param station1 String
     * @param station2 String
     * @param date     String
     * @return Map<Schedule,Integer>
     */
    Map<Schedule, Integer> searchTrains(String station1, String station2, String date);

    /**
     * Get train route for client request.
     *
     * @param trainId  long
     * @param station1 String
     * @param station2 String
     * @return List<Schedule>
     */
    List<Schedule> getCurrentRoute(long trainId, String station1, String station2);

    /**
     * Get ticket price.
     *
     * @param currentRoute List<Schedule>.
     * @param trainId      long.
     * @return int.
     */
    int getTicketPrice(long trainId, List<Schedule> currentRoute);

    /**
     * Get count of free seats in train.
     *
     * @param month    String
     * @param day      String
     * @param year     String
     * @param id       long
     * @param station1 String
     * @param station2 String
     * @return int
     */
    int getFreeSeats(String month,
                     String day,
                     String year,
                     long id,
                     String station1,
                     String station2);

    /**
     * Buy ticket and return registered passenger with ticket.
     *
     * @param firstName String
     * @param lastName String
     * @param date String
     * @param trainId long
     * @param departDay String
     * @param arriveDay String
     * @param departStation String
     * @param arriveStation String
     * @param ticketPrice int
     */
    void buyTicket(
            String firstName,
            String lastName,
            String date,
            long trainId,
            String departDay,
            String arriveDay,
            String departStation,
            String arriveStation,
            int ticketPrice
    );
}
