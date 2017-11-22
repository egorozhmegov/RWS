package service.interfaces;

import model.RailWayStation;
import model.Schedule;
import util.ScheduleWrapper;
import util.StationWrapper;
import util.TicketData;
import util.TrainWrapper;

import java.time.LocalDate;
import java.util.List;

/**
 * Client service interface.
 */
public interface ClientService {

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param request TrainWrapper
     * @return List<TrainWrapper>
     */
    List<TrainWrapper> searchTrains(TrainWrapper request);

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
     * @param departDate LocalDate
     * @param id long
     * @param station1 String
     * @param station2 String
     * @return int
     */
    int getFreeSeats(LocalDate departDate,
                     long id,
                     String station1,
                     String station2);

    /**
     * Buy ticket and return registered passenger with ticket.
     *
     * @param ticketData TicketData
     */
    void buyTicket(TicketData ticketData);

    /**
     * Get route point date.
     *
     * @param departDay LocalDate
     * @param routePoint Schedule
     * @return LocalDate
     */
    LocalDate getRoutePointDate (LocalDate departDay, Schedule routePoint);

    /**
     *Get number of week day.
     *
     * @param date LocalDate
     * @return int
     */int dayOfWeek(LocalDate date);

    /**
     * Get all stations.
     *
     * @return List<RailWayStation>.
     */
    List<RailWayStation> getAllStations();

    /**
     *Get two lists: first - list of arrival schedule, second - list of departure schedule.
     *
     * @param stationWrapper StationWrapper
     * @return ScheduleWrapper
     */
    ScheduleWrapper getSchedule(StationWrapper stationWrapper);

}
