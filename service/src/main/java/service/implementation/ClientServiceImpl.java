package service.implementation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import exception.*;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.*;
import util.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Client service implementation.
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
     * Get two lists: first - list of arrival schedule, second - list of departure schedule.
     *
     * @param stationWrapper StationWrapper
     * @return ScheduleWrapper
     */
    @Override
    public ScheduleWrapper getSchedule(StationWrapper stationWrapper) {
        ScheduleWrapper schedule = new ScheduleWrapper();
        LOG.info(String.format("Loaded arrival trains: %s and departure trains: %s",
                schedule.getArrivalSchedule(),
                schedule.getDepartureSchedule()));
        schedule.setArrivalSchedule(scheduleService.getArriveSchedule(stationWrapper));
        schedule.setDepartureSchedule(scheduleService.getDepartSchedule(stationWrapper));
        return schedule;
    }

    /**
     * Get all stations.
     *
     * @return List<RailWayStation>.
     */
    @Override
    public List<RailWayStation> getAllStations() {
        LOG.info("All stations loaded");
        return stationService.getAll();
    }

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param request TrainWrapper
     * @return List<TrainWrapper>
     */
    @Override
    public List<TrainWrapper> searchTrains(TrainWrapper request) {

        List<TrainWrapper> searchResult = new ArrayList<>();

        String stationFrom = request.getStationFrom().getTitle();
        String stationTo = request.getStationTo().getTitle();
        LocalDate departDate = request.getDepartDate();

        if (stationFrom == null
                || stationFrom.trim().isEmpty()
                || stationTo == null
                || stationTo.trim().isEmpty()
                || departDate == null) {
            LOG.error("Not valid search data.");
            throw new ClientServiceException("Not valid search data.");
        }

        RailWayStation departStation = stationService.getStationByTitle(stationFrom);
        RailWayStation arriveStation = stationService.getStationByTitle(stationTo);
        int day = dayOfWeek(departDate);

        List<Schedule> trains = scheduleService
                .searchTrain(departStation.getId(), arriveStation.getId(), day);

        for (Schedule schedule : trains) {
            TrainWrapper trainWrapper = new TrainWrapper();

            Train train = schedule.getTrain();
            List<Schedule> route = getCurrentRoute(train.getId(), stationFrom, stationTo);
            LocalTime arriveTime = route.get(route.size() - 1).getArrivalTime();

            trainWrapper.setTrain(train);
            trainWrapper.setStationFrom(request.getStationFrom());
            trainWrapper.setStationTo(request.getStationTo());
            trainWrapper.setDepartTime(schedule.getDepartureTime());
            trainWrapper.setArriveTime(arriveTime);
            trainWrapper.setDepartDate(departDate);
            trainWrapper.setArriveDate(getRoutePointDate(departDate, route.get(route.size() - 1)));
            trainWrapper.setPrice(getTicketPrice(train.getId(), route));
            trainWrapper.setRoute(route);
            trainWrapper.setSeats(getFreeSeats(departDate, train.getId(), stationFrom, stationTo));

            searchResult.add(trainWrapper);
        }

        if (searchResult.size() == 0) {
            LOG.error(String.format("No trains for request: %s - %s on date %s",
                    stationFrom, stationTo, departDate));
            throw new ClientServiceNoTrainsException(
                    String.format("No trains for request: %s - %s on date %s",
                            stationFrom, stationTo, departDate));
        }

        LOG.info(String.format("Search trains: '%s'", searchResult));
        return searchResult;
    }


    /**
     * Get number of week day.
     *
     * @param date LocalDate
     * @return int
     */
    @Override
    public int dayOfWeek(LocalDate date) {
        return TrainServiceImpl
                .WEEK_DAYS
                .indexOf(date
                        .getDayOfWeek()
                        .toString()
                        .toLowerCase()
                        .substring(0, 3)) + 1;
    }

    /**
     * Parse string date to object LocalDate.
     *
     * @param date String
     * @return LocalDate
     */
    @Override
    public LocalDate parseDate(String date) {
        return LocalDate.of(
                Integer.parseInt(date.split("/")[2]),
                Integer.parseInt(date.split("/")[0]),
                Integer.parseInt(date.split("/")[1]));
    }

    /**
     * Parse string date with dash to LocalDate format.
     *
     * @param date String
     * @return LocalDate
     */
    @Override
    public LocalDate parseDashDate(String date) {
        return LocalDate.of(
                Integer.parseInt(date.split("-")[0]),
                Integer.parseInt(date.split("-")[1]),
                Integer.parseInt(date.split("-")[2]));
    }

    /**
     * Get train route for client request.
     *
     * @param trainId  long
     * @param station1 String
     * @param station2 String
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getCurrentRoute(long trainId, String station1, String station2) {
        List<Schedule> trainRoute = trainService.getRoute(trainId);
        List<Schedule> currentRoute = new ArrayList<>();

        boolean isCurrentRoute = false;
        for (Schedule routePoint : trainRoute) {
            if (Objects.equals(routePoint.getStation().getTitle(), station1)) {
                isCurrentRoute = true;
            } else if (Objects.equals(routePoint.getStation().getTitle(), station2)) {
                currentRoute.add(routePoint);
                isCurrentRoute = false;
            }
            if (isCurrentRoute) currentRoute.add(routePoint);
        }
        LOG.info(String.format("Route from %s to %s of train (id = %s) loaded",
                station1,
                station2,
                trainId));
        return currentRoute;
    }

    /**
     * Get ticket price.
     *
     * @param currentRoute List<Schedule>.
     * @param trainId      long.
     * @return int.
     */
    @Override
    public int getTicketPrice(long trainId, List<Schedule> currentRoute) {
        return trainService
                .read(trainId)
                .getTariff() * currentRoute.size();
    }

    /**
     * Get count of free seats in train.
     *
     * @param departDate LocalDate
     * @param id         long
     * @param station1   String
     * @param station2   String
     * @return int
     */
    @Override
    public int getFreeSeats(LocalDate departDate,
                            long id,
                            String station1,
                            String station2) {
        long departStationId = stationService.getStationByTitle(station1).getId();
        long arriveStationId = stationService.getStationByTitle(station2).getId();

        return Train.SEATS - passengerService
                .getRegisteredPassengers(
                        id,
                        departStationId,
                        arriveStationId,
                        departDate)
                .size();
    }


    /**
     * Buy ticket and return registered passenger with ticket.
     *
     * @param ticketData TicketData
     */
    @Transactional
    @Override
    public void buyTicket(TicketData ticketData) {
        long trainId = ticketData.getTrainWrapper().getTrain().getId();
        LocalDate departDate = ticketData.getTrainWrapper().getDepartDate();
        String departStation = ticketData.getTrainWrapper().getStationFrom().getTitle();
        String arriveStation = ticketData.getTrainWrapper().getStationTo().getTitle();
        int ticketPrice = ticketData.getTrainWrapper().getPrice();

        if (ticketData.getPassenger().getFirstName().isEmpty()
                || ticketData.getPassenger().getLastName().isEmpty()
                || ticketData.getPassenger().getBirthday() == null
                || trainId == 0
                || departDate == null
                || departStation.isEmpty()
                || ticketPrice == 0) {
            LOG.error("Invalid payment data.");
            throw new ClientServiceException("Invalid payment data.");
        }

        List<Schedule> currentRoute = getCurrentRoute(trainId, departStation, arriveStation);
        long departStationId = stationService.getStationByTitle(departStation).getId();
        long arriveStationId = stationService.getStationByTitle(arriveStation).getId();

        LocalTime depTime = currentRoute.get(0).getDepartureTime();

        if (passengerService.getRegisteredPassenger(
                trainId,
                departStationId,
                arriveStationId,
                departDate,
                ticketData.getPassenger()) != null
                ) {
            LOG.error(String.format("Passengers: %s registered already.", ticketData.getPassenger()));
            throw new ClientServiceRegisteredPassengerException(String
                    .format("Passengers: %s registered already.", ticketData.getPassenger()));
        }

        if (departDate.isBefore(LocalDate.now())) {
            LOG.error(String.format("Date: %s is before now.", departDate));
            throw new ClientServiceTimeOutException(String.format("Date: %s is before now.", departDate));
        }

        if (departDate == LocalDate.now()
                && (MINUTES.between(depTime, LocalTime.now()) < 10
                || depTime.isBefore(LocalTime.now()))) {
            LOG.error(String.format("Time: %s is invalid.", depTime));
            throw new ClientServiceTimeOutException(String.format("Time: %s is invalid.", depTime));
        }

        if (ticketData.getTrainWrapper().getSeats() == 0) {
            LOG.error("No free seats.");
            throw new ClientServiceNoSeatsException("No free seats.");
        }

        Ticket ticket = new Ticket(ticketPrice);
        ticketService.create(ticket);

        for (Schedule schedule : currentRoute) {
            Passenger passenger = new Passenger(
                    ticketData.getPassenger().getFirstName(),
                    ticketData.getPassenger().getLastName(),
                    ticketData.getPassenger().getBirthday()
            );
            if (schedule.getArrivalDay() == 0) {
                passenger.setTrainDate(departDate);
            } else {
                passenger.setTrainDate(getRoutePointDate(departDate, schedule));
            }
            passenger.setTrain(trainService.read(trainId));
            passenger.setStation(schedule.getStation());
            passenger.setTicket(ticket);
            passengerService.create(passenger);
        }

        long ticketNumber = passengerService.getRegisteredPassenger(
                trainId,
                departStationId,
                arriveStationId,
                departDate,
                ticketData.getPassenger()).getId();

        sendTicketOnEmail(ticketNumber, ticketData);
        LOG.info(String.format("Passenger %s registered", ticketData.getPassenger()));
    }

    /**
     * Send ticket on email of user client.
     *
     * @param ticketData TicketData
     */
    public void sendTicketOnEmail(long ticketNumber, TicketData ticketData){
        createQRCode(ticketNumber, ticketData);

        EmailSender sender = new EmailSender(ticketNumber);
        sender.send(
                "RWS TICKET SUPPORT",
                "Thank you for using the services of our company!",
                ticketData.getUserEmail()
        );
    }

    public void createQRCode(long ticketNumber, TicketData ticketData) {
        try{
            Document ticket = new Document(new Rectangle(160, 160));
            PdfWriter writer = PdfWriter
                    .getInstance(ticket, new FileOutputStream(String
                            .format("C:/Users/Egor/Desktop/RWS/service/src/main/java/util/tickets/ticket_%s.pdf", ticketNumber)));
            ticket.open();

            ticket.add(new Paragraph(String.format("Ticket № %s", ticketNumber)));

            String train = String.format("Train: %s", ticketData.getTrainWrapper().getTrain().getNumber());
            String direction = String.format("Direction: %s - %s",
                    ticketData.getTrainWrapper().getStationFrom().getTitle(),
                    ticketData.getTrainWrapper().getStationTo().getTitle());
            String departure = String.format("Departure: %s  %s",
                    ticketData.getTrainWrapper().getDepartDate().toString(),
                    ticketData.getTrainWrapper().getDepartTime().toString());
            String arrival = String.format("Arrival: %s  %s",
                    ticketData.getTrainWrapper().getArriveDate().toString(),
                    ticketData.getTrainWrapper().getArriveTime().toString());
            String lastName = String.format("Last Name: %s", ticketData.getPassenger().getLastName());
            String firstName = String.format("First Name: %s", ticketData.getPassenger().getFirstName());
            String birthday = String.format("Birthday: %s", ticketData.getPassenger().getBirthday());
            String finala = String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
                    train, direction, departure, arrival, firstName, lastName, birthday);

            BarcodeQRCode ticketCode = new BarcodeQRCode(finala, 1, 1, null);
            Image qrImage = ticketCode.getImage();
            ticket.add(qrImage);

            ticket.close();
        } catch(FileNotFoundException | DocumentException e){
            LOG.error(e.getMessage());
        }
    }

    /**
     * Get route point date.
     *
     * @param departDay  LocalDate
     * @param routePoint Schedule
     * @return LocalDate
     */
    @Override
    public LocalDate getRoutePointDate(LocalDate departDay, Schedule routePoint) {
        int depYear = departDay.getYear();
        int depMonth = departDay.getMonthValue();
        int depDay = departDay.getDayOfMonth();
        int weekDay = TrainServiceImpl
                .WEEK_DAYS.indexOf(departDay
                        .getDayOfWeek()
                        .toString()
                        .substring(0, 3)
                        .toLowerCase()) + 1;

        int routePointDay;
        int routePointMonth = depMonth;
        int routePointYear = depYear;

        int diffDays = routePoint.getArrivalDay() - weekDay;
        if (diffDays < 0) routePointDay = 7 + diffDays + depDay;
        else routePointDay = depDay + diffDays;

        if (routePointDay > 31
                && (routePointMonth == 1
                || routePointMonth == 2
                || routePointMonth == 3
                || routePointMonth == 5
                || routePointMonth == 7
                || routePointMonth == 8
                || routePointMonth == 10
                || routePointMonth == 12)) {
            routePointDay -= 31;
            routePointMonth++;
        } else if (routePointDay > 30
                && (routePointMonth == 4
                || routePointMonth == 6
                || routePointMonth == 9
                || routePointMonth == 11)) {
            routePointDay -= 30;
            routePointMonth++;
        } else if (routePointDay > 28
                && (routePointMonth == 2)) {
            routePointDay -= 28;
            routePointMonth++;
        }

        if (routePointMonth > 12) {
            routePointMonth -= 12;
            routePointYear++;
        }

        return LocalDate.of(routePointYear, routePointMonth, routePointDay);
    }
}
