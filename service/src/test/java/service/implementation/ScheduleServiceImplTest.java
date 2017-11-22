package service.implementation;

import dao.interfaces.ScheduleDao;
import model.RailWayStation;
import model.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.ClientService;
import service.interfaces.RailWayStationService;
import util.StationWrapper;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceImplTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleDao scheduleDao;

    @Mock
    private RailWayStationService railWayStationService;

    @Mock
    private ClientService clientService;

    @Test
    public void deleteByTrainId0(){
        scheduleService.deleteByTrainId(1L);
        verify(scheduleDao).deleteByTrainId(1L);
    }

    @Test
    public void deleteByStationId0(){
        scheduleService.deleteByStationId(1L);
        verify(scheduleDao).deleteByStationId(1L);
    }

    @Test
    public void searchTrain0(){
        List<Schedule> result = new ArrayList<>();
        when(scheduleDao.searchTrain(1L,1L,1))
                .thenReturn(result);
        assertEquals(result, scheduleService.searchTrain(1L,1L,1));
    }

    @Test
    public void getDepartSchedule0(){
        List<Schedule> result = new ArrayList<>();
        StationWrapper stationWrapper = new StationWrapper();
        when(railWayStationService
                .getStationByTitle(stationWrapper.getStation()))
                .thenReturn(new RailWayStation());
        when(clientService.dayOfWeek(stationWrapper.getDate()))
                .thenReturn(1);
        when(scheduleDao.getStationDepartSchedule(1L, 1))
                .thenReturn(result);
        assertEquals(result, scheduleService.getDepartSchedule(stationWrapper));
    }

    @Test
    public void getArrivalSchedule0(){
        List<Schedule> result = new ArrayList<>();
        StationWrapper stationWrapper = new StationWrapper();
        when(railWayStationService
                .getStationByTitle(stationWrapper.getStation()))
                .thenReturn(new RailWayStation());
        when(clientService.dayOfWeek(stationWrapper.getDate()))
                .thenReturn(1);
        when(scheduleDao.getStationArriveSchedule(1L, 1))
                .thenReturn(result);
        assertEquals(result, scheduleService.getArriveSchedule(stationWrapper));
    }

    @Test
    public void deleteByStationAndTrainId0(){
        scheduleService.deleteByStationAndTrainId(1L,1L);
        verify(scheduleDao).deleteByStationAndTrainId(1L, 1L);
    }
}