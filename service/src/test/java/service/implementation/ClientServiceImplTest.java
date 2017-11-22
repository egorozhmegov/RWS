package service.implementation;

import exception.ClientServiceNoTrainsException;
import model.RailWayStation;
import model.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;
import util.ScheduleWrapper;
import util.StationWrapper;
import util.TicketData;
import util.TrainWrapper;

import java.time.LocalDate;
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
    RailWayStationService stationService;

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

    @Test(expected = ClientServiceNoTrainsException.class)
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
    public void getCurrentRoute(){
        List<Schedule> currentRoute = new ArrayList<>();
        long trainId  = 1;
        String station1 = "1";
        String station2 = "2";
        when(clientServiceMock.getCurrentRoute(trainId, station1, station2)).thenReturn(currentRoute);
        assertEquals(currentRoute, clientServiceMock.getCurrentRoute(trainId, station1, station2));
    }

    @Test
    public void getTicketPrice(){
        long trainId = 1;
        List<Schedule> currentRoute = new ArrayList<>();
        when(clientServiceMock.getTicketPrice(trainId, currentRoute)).thenReturn(100);
        assertEquals(100, clientServiceMock.getTicketPrice(trainId, currentRoute));
    }

    @Test
    public void getFreeSeats0(){
        LocalDate departDate = LocalDate.of(2017, 10,10);
        long id  = 1;
        String station1 = "1";
        String station2 = "2";
        int freeSeats = 92;
        when(clientServiceMock.getFreeSeats(departDate, id, station1, station2)).thenReturn(92);
        assertEquals(92, clientServiceMock.getFreeSeats(departDate, id, station1, station2));
    }

    @Test
    public void buyTicket0(){
        TicketData ticketData = new TicketData();
        clientServiceMock.buyTicket(ticketData);
        verify(clientServiceMock).buyTicket(ticketData);
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