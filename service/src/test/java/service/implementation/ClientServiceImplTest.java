package service.implementation;

import com.itextpdf.text.DocumentException;
import exception.ServiceException;
import model.Passenger;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;
import service.interfaces.TrainService;
import util.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientServiceMock;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private RailWayStationService stationService;

    @Mock
    private TrainService trainService;

    @Mock
    private PassengerService passengerService;

    private ClientServiceImpl clientService = new ClientServiceImpl();

    @Test
    public void getSchedule0(){
        StationWrapper stationWrapper = new StationWrapper();
        ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
        when(scheduleService.getArriveSchedule(stationWrapper)).thenReturn(new ArrayList<Schedule>());
        when(scheduleService.getDepartSchedule(stationWrapper)).thenReturn(new ArrayList<Schedule>());
        assertNotEquals(scheduleWrapper, clientServiceMock.getSchedule(stationWrapper));
    }

    @Test
    public void getAllStations0(){
        List<RailWayStation> stations = new ArrayList<>();
        when(stationService.getAll()).thenReturn(stations);
        assertEquals(stations, clientServiceMock.getAllStations());
    }

    @Test(expected = ServiceException.class)
    public void searchTrains0(){
        List<TrainWrapper> searchResult = new ArrayList<>();
        TrainWrapper request = new TrainWrapper();
        request.setStationFrom(new RailWayStation("a"));
        request.setStationTo(new RailWayStation("b"));
        request.setDepartDate(LocalDate.of(2017, 10, 13));
        when(stationService.getStationByTitle("a")).thenReturn(new RailWayStation());
        when(stationService.getStationByTitle("b")).thenReturn(new RailWayStation());
        when(scheduleService.searchTrain(1L, 1L, 1)).thenReturn(new ArrayList<Schedule>());
        when(clientServiceMock.searchTrains(request)).thenReturn(searchResult);
        assertEquals(searchResult, clientServiceMock.searchTrains(request));
    }

    @Test
    public void getCurrentRoute0(){
        List<Schedule> currentRoute = new ArrayList<>();
        when(trainService.getRoute(1L)).thenReturn(currentRoute);
        when(clientServiceMock.getCurrentRoute(1L, "", "")).thenReturn(currentRoute);
        assertEquals(currentRoute, clientServiceMock.getCurrentRoute(1L, "", ""));
    }

    @Test
    public void getTicketPrice0(){
        when(trainService.read(1L)).thenReturn(new Train());
        List<Schedule> currentRoute = new ArrayList<>();
        assertEquals(0, clientServiceMock.getTicketPrice(1L, currentRoute));
    }

    @Test
    public void getFreeSeats0(){
        LocalDate departDate = LocalDate.of(2017, 10,10);
        when(stationService.getStationByTitle("")).thenReturn(new RailWayStation());
        when(passengerService
                .getRegisteredPassenger(1L, 1L, 1L, departDate, new Passenger()))
        .thenReturn(new Passenger());
        assertEquals(92, clientServiceMock.getFreeSeats(departDate, 1L, "", ""));
    }

    @Test(expected = NullPointerException.class)
    public void buyTicket0(){
        TicketData ticketData = new TicketData();
        Train train = new Train();
        train.setId(1L);
        TrainWrapper trainWrapper = new TrainWrapper();
        trainWrapper.setTrain(train);
        trainWrapper.setDepartDate(LocalDate.of(2017, 10, 14));
        trainWrapper.setStationFrom(new RailWayStation("a"));
        trainWrapper.setStationTo(new RailWayStation("b"));
        trainWrapper.setPrice(100);
        Passenger passenger
                = new Passenger("Egor", "Ozhmegov", LocalDate.of(2017, 10, 14));
        ticketData.setPassenger(passenger);
        ticketData.setTrainWrapper(trainWrapper);
        RailWayStation railWayStation = new RailWayStation("ab");
        railWayStation.setId(1L);
        List<Schedule> currentRoute = new ArrayList<>();
        Schedule schedule1 = new Schedule();
        schedule1.setDepartureTime(LocalTime.of(0, 0));
        currentRoute.add(schedule1);
        when(passengerService.getRegisteredPassenger(
                1L,
                1L,
                1L,
                ticketData.getTrainWrapper().getDepartDate(),
                ticketData.getPassenger())).thenReturn(passenger);
        when(stationService.getStationByTitle("a")).thenReturn(railWayStation);
        when(stationService.getStationByTitle("b")).thenReturn(railWayStation);
        when(clientServiceMock.getCurrentRoute(1L, "a", "b"))
                .thenReturn(currentRoute);
        clientServiceMock.buyTicket(ticketData);
    }

    @Test(expected = Exception.class)
    public void sendTicketOnEmail0(){
        TicketData ticketData = new TicketData();
        EmailSender sender = new EmailSender(1L);
        sender.send("");
        verify(clientServiceMock).sendTicketOnEmail(1L, ticketData);
    }

    @Test
    public void createQRCode0() throws FileNotFoundException, DocumentException {
        TicketData ticketData = new TicketData();
        Train train = new Train();
        train.setNumber("1");
        train.setId(1L);
        TrainWrapper trainWrapper = new TrainWrapper();
        trainWrapper.setTrain(train);
        trainWrapper.setDepartDate(LocalDate.of(2017, 10, 14));
        RailWayStation station1 = new RailWayStation("a");
        RailWayStation station2 = new RailWayStation("b");
        trainWrapper.setStationFrom(station1);
        trainWrapper.setStationTo(station2);
        trainWrapper.setPrice(100);

        ticketData.setTrainWrapper(trainWrapper);

        clientServiceMock.createQRCode(1L, ticketData);
    }

    @Test
    public void dayOfWeek0(){
        assertEquals(2, clientService
                .dayOfWeek(LocalDate.of(2017, 10, 9)));
    }

    @Test
    public void getRoutePointDate0(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(6);

        assertEquals(LocalDate.of(2017, 10, 13),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 13), schedule));
    }

    @Test
    public void getRoutePointDate1(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(6);

        assertNotEquals(LocalDate.of(2017, 10, 14),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 13), schedule));
    }

    @Test
    public void getRoutePointDate2(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(7);

        assertNotEquals(LocalDate.of(2017, 10, 13),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 13), schedule));
    }

    @Test
    public void getRoutePointDate3(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(1);

        assertEquals(LocalDate.of(2017, 10, 15),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 14), schedule));
    }

    @Test
    public void getRoutePointDate4(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(1);

        assertEquals(LocalDate.of(2017, 10, 15),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 14), schedule));
    }

    @Test
    public void getRoutePointDate5(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(4);

        assertEquals(LocalDate.of(2017, 11, 1),
                clientService.getRoutePointDate(LocalDate.of(2017, 10, 31), schedule));
    }

    @Test
    public void getRoutePointDate6(){
        Schedule schedule = new Schedule();
        schedule.setArrivalDay(2);

        assertEquals(LocalDate.of(2018, 1, 1),
                clientService.getRoutePointDate(LocalDate.of(2017, 12, 31), schedule));
    }


}