package service.implementation;

import dao.interfaces.PassengerDao;
import model.Passenger;
import model.RailWayStation;
import model.Schedule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.ClientService;
import service.interfaces.RailWayStationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassengerServiceImpTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerDao passengerDao;

    @Mock
    private ClientService clientService;

    @Mock
    private RailWayStationService railWayStationService;

    private List<Schedule> currenRoute = new ArrayList<>();

    @Before
    public void init(){
        currenRoute.add(new Schedule());
        currenRoute.add(new Schedule());
    }
    @Test
    public void getRegisteredPassengers0(){
        when(railWayStationService.read(1L)).thenReturn(new RailWayStation());
        when(clientService.getCurrentRoute(1L,
                railWayStationService.read(1L).getTitle(),
                railWayStationService.read(1L).getTitle()))
                .thenReturn(currenRoute);
        when(clientService.getRoutePointDate(LocalDate.of(2017, 10, 10), new Schedule()))
                .thenReturn(LocalDate.of(2017, 10, 10));
        when(passengerDao.getRegisteredPassengers(1L, LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 10)))
                .thenReturn(new ArrayList<Passenger>());

        assertEquals(0, passengerService.getRegisteredPassengers(1L, 1L, 1L, LocalDate.of(2017, 10, 10)).size());
    }

    @Test
    public void getRegisteredPassenger0(){
        when(railWayStationService.read(1L)).thenReturn(new RailWayStation());
        when(clientService.getCurrentRoute(1L,
                railWayStationService.read(1L).getTitle(),
                railWayStationService.read(1L).getTitle()))
                .thenReturn(currenRoute);
        when(clientService.getRoutePointDate(LocalDate.of(2017, 10, 10), new Schedule()))
                .thenReturn(LocalDate.of(2017, 10, 10));
        when(passengerDao.getRegisteredPassenger(1L, LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 10), new Passenger()))
                .thenReturn(null);
        assertEquals(null, passengerService.getRegisteredPassenger(1L, 1L, 1L, LocalDate.of(2017, 10, 10), new Passenger()));
    }

    @Test
    public void deleteByTrainId0(){
        long trainId = 1;
        passengerService.deleteByTrainId(trainId);
        verify(passengerDao).deleteByTrainId(trainId);
    }

    @Test
    public void deleteByStationId0(){
        long stationId = 1;
        passengerService.deleteByStationId(stationId);
        verify(passengerDao).deleteByStationId(stationId);
    }
}