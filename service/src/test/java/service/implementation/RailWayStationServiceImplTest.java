package service.implementation;

import dao.interfaces.RailWayStationDao;
import model.RailWayStation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RailWayStationServiceImplTest {

    @InjectMocks
    private RailWayStationServiceImpl railWayStationService;

    @Mock
    private RailWayStationDao railWayStationDao;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private PassengerService passengerService;

    @Test
    public void removeStation0(){
        railWayStationService.removeStation(1L);
        verify(scheduleService).deleteByStationId(1L);
    }

    @Test
    public void removeStation1(){
        railWayStationService.removeStation(1L);
        verify(railWayStationDao).delete(1L);
    }

    @Test
    public void removeStation2(){
        railWayStationService.removeStation(1L);
        verify(passengerService).deleteByStationId(1L);
    }

    @Test
    public void getStationByTitle0(){
        RailWayStation station = new RailWayStation();
        when(railWayStationService.getStationByTitle("")).thenReturn(station);
        assertEquals(station, railWayStationService.getStationByTitle(""));
    }

    @Test
    public void addStation0(){
        RailWayStation station = new RailWayStation("a");
        when(railWayStationService.getStationByTitle("")).thenReturn(station);
        railWayStationService.addStation(station);
        verify(railWayStationDao).create(station);
    }

    @Test
    public void isExistStation(){
        when(railWayStationDao.getStationByTitle("")).thenReturn(null);
        assertEquals(null, railWayStationService.getStationByTitle(""));
    }
}