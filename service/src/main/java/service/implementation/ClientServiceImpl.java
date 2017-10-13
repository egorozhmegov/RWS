package service.implementation;

import exception.ClientServiceException;
import model.RailWayStation;
import model.Schedule;
import model.Ticket;
import model.Train;
import model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.*;
import java.time.LocalDate;
import java.util.*;

/**
 *Client service implementation.
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private final static Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RailWayStationService stationService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TicketService ticketService;

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param station1 String
     * @param station2 String
     * @param date String
     * @return Map
     */
    @Override
    public Map<Schedule, Integer> searchTrains(String station1,
                                               String station2,
                                               String date){

        Map<Schedule, Integer> searchResult = new HashMap<>();

        if(station1 == null
                || station1.trim().isEmpty()
                || station2 == null
                || station2.trim().isEmpty()
                || date == null
                || date.trim().isEmpty()){
            LOG.info("Not valid search data.");
            throw new ClientServiceException("Not valid search data.");
        }
        RailWayStation departStation = stationService.getStationByTitle(station1);
        RailWayStation arriveStation = stationService.getStationByTitle(station2);
        int day = dayOfWeek(parseDate(date));

        List<Schedule> trains = scheduleService
                .searchTrain(departStation.getId(), arriveStation.getId(), day);

        for(Schedule schedule: trains){
            Train train = schedule.getTrain();
            searchResult.put(schedule,
                    getTicketPrice(train.getId(),
                            getCurrentRoute(train.getId(),
                                    station1, station2)));
        }

        LOG.info(String.format("Search trains: '%s'", searchResult));
        return searchResult;
    }


    /**
     *Get number of week day.
     *
     * @param date LocalDate
     * @return int
     */
    public int dayOfWeek(LocalDate date){
        return TrainServiceImpl
                .WEEK_DAYS
                .indexOf(date
                        .getDayOfWeek()
                        .toString()
                        .toLowerCase()
                        .substring(0,3)) + 1;
    }

    /**
     * Parse string date to object LocalDate.
     *
     * @param date String
     * @return LocalDate
     */
    public LocalDate parseDate(String date){
        return LocalDate.of(
                Integer.parseInt(date.split("/")[2]),
                Integer.parseInt(date.split("/")[0]),
                Integer.parseInt(date.split("/")[1]));
    }

    public LocalDate parseDashDate(String date){
        return LocalDate.of(
                Integer.parseInt(date.split("-")[0]),
                Integer.parseInt(date.split("-")[1]),
                Integer.parseInt(date.split("-")[2]));
    }

    /**
     * Get train route for client request.
     *
     * @param trainId long
     * @param station1 String
     * @param station2 String
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getCurrentRoute(long trainId, String station1, String station2) {
        List<Schedule> trainRoute = trainService.getRoute(trainId);
        List<Schedule> currentRoute = new ArrayList<>();

        boolean isCurrentRoute = false;
        for (Schedule routePoint: trainRoute){
            if(Objects.equals(routePoint.getStation().getTitle(), station1)){
                isCurrentRoute = true;
            } else if(Objects.equals(routePoint.getStation().getTitle(), station2)){
                currentRoute.add(routePoint);
                isCurrentRoute = false;
            }
            if(isCurrentRoute) currentRoute.add(routePoint);
        }
        return currentRoute;
    }

    /**
     * Get ticket price.
     *
     * @param currentRoute List<Schedule>.
     * @param trainId long.
     * @return int.
     */
    @Override
    public int getTicketPrice(long trainId, List<Schedule> currentRoute) {
        return trainService
                .read(trainId)
                .getTariff()*currentRoute.size();
    }

    /**
     * Get count of free seats in train.
     *
     * @param month String
     * @param day String
     * @param year String
     * @param id long
     * @param station1 String
     * @param station2 String
     * @return int
     */
    @Override
    public int getFreeSeats(String month,
                     String day,
                     String year,
                     long id,
                     String station1,
                     String station2) {
        long departStationId = stationService.getStationByTitle(station1).getId();
        long arriveStationId = stationService.getStationByTitle(station2).getId();
        LocalDate departDay = LocalDate
                    .of(Integer.parseInt(year),
                        Integer.parseInt(month),
                        Integer.parseInt(day));


        return Train.SEATS - passengerService
                .getRegisteredPassengers(
                    id,
                    departStationId,
                    arriveStationId,
                    departDay)
                .size();
    }

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
    @Override
    public void buyTicket(
            String firstName,
            String lastName,
            String date,
            long trainId,
            String departDay,
            String arriveDay,
            String departStation,
            String arriveStation,
            int ticketPrice) {
        if(firstName.isEmpty()
                || lastName.isEmpty()
                || date.isEmpty()
                || trainId == 0
                || departDay.isEmpty()
                || arriveDay.isEmpty()
                || departStation.isEmpty()
                || ticketPrice == 0){
            LOG.info("Invalid payment data.");
            throw new ClientServiceException("Invalid payment data.");
        }

        long departStationId = stationService.getStationByTitle(departStation).getId();
        long arriveStationId = stationService.getStationByTitle(arriveStation).getId();
        LocalDate depDay = parseDashDate(departDay);

        Passenger passenger = new Passenger(firstName, lastName, parseDate(date));
        passenger.setTrain(trainService.read(trainId));

        if(passengerService
                .getRegisteredPassenger(
                        trainId,
                        departStationId,
                        arriveStationId,
                        passenger) != null){
            LOG.info("Passengers registered already.");
            throw new ClientServiceException("Passengers registered already.");
        }

        Ticket ticket = new Ticket(ticketPrice);
        ticketService.create(ticket);
        passenger.setTicket(ticket);

        for(Schedule schedule: getCurrentRoute(trainId, departStation, arriveStation)){
            if(schedule.getArrivalDay() == 0){
                passenger.setTrainDate(depDay);
            } else {
                passenger.setTrainDate(getRoutePointDate(depDay, schedule));
            }
            passenger.setStation(schedule.getStation());
            passengerService.create(passenger);
        }

    }

    /**
     * Get route point date.
     *
     * @param departDay LocalDate
     * @param routePoint Schedule
     * @return LocalDate
     */
    public LocalDate getRoutePointDate (LocalDate departDay, Schedule routePoint){
        int depYear = departDay.getYear();
        int depMonth = departDay.getMonthValue();
        int depDay = departDay.getDayOfMonth();
        int weekDay = TrainServiceImpl
                .WEEK_DAYS.indexOf(departDay
                        .getDayOfWeek()
                        .toString()
                        .substring(0,3)
                        .toLowerCase()) + 1;

        int routePointDay;
        int routePointMonth = depMonth;
        int routePointYear = depYear;

        int diffDays = routePoint.getArrivalDay() - weekDay;
        if(diffDays < 0) routePointDay = 7 + diffDays + depDay;
        else routePointDay = depDay + diffDays;

        if(routePointDay > 31
                && (routePointMonth == 1
                || routePointMonth == 2
                || routePointMonth == 3
                || routePointMonth == 5
                || routePointMonth == 7
                || routePointMonth == 8
                || routePointMonth == 10
                || routePointMonth == 12)){
            routePointDay -= 31;
            routePointMonth++;
        } else if(routePointDay > 30
                && (routePointMonth == 4
                || routePointMonth == 6
                || routePointMonth == 9
                || routePointMonth == 11)){
            routePointDay -= 30;
            routePointMonth++;
        } else if(routePointDay > 28
                && (routePointMonth == 2)){
            routePointDay -= 28;
            routePointMonth++;
        }

        if(routePointMonth > 12){
            routePointMonth -= 12;
            routePointYear++;
        }

        return LocalDate.of(routePointYear, routePointMonth, routePointDay);
    }
}
