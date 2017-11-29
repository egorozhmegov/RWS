package service.implementation;

import dao.interfaces.TrainDao;
import exception.TrainRoutePointAddException;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceImplTest {

    @InjectMocks
    private TrainServiceImpl trainServiceMock;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private RailWayStationService railWayStationService;

    @Mock
    private TrainDao trainDao;

    @Mock
    private PassengerService passengerService;

    private TrainServiceImpl trainService = new TrainServiceImpl();

    private List<Schedule> route = new ArrayList<>();


    @Before
    public void initRoute1(){
        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("a"));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("b"));
        point2.setArrivePeriod("mon,tue,fri");
        point2.setArrivalTime(LocalTime.of(4,30));
        point2.setDepartPeriod("mon,tue,fri");
        point2.setDepartureTime(LocalTime.of(4,50));

        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("c"));
        point3.setArrivePeriod("mon,tue,fri");
        point3.setArrivalTime(LocalTime.of(12,50));
        point3.setDepartPeriod("mon,tue,fri");
        point3.setDepartureTime(LocalTime.of(13,15));

        Schedule point4 = new Schedule();
        point4.setStation(new RailWayStation("d"));
        point4.setArrivePeriod("tue,wed,sat");
        point4.setArrivalTime(LocalTime.of(8,50));
        point4.setDepartPeriod("tue,wed,sat");
        point4.setDepartureTime(LocalTime.of(9,30));

        Schedule point5 = new Schedule();
        point5.setStation(new RailWayStation("e"));
        point5.setArrivePeriod("tue,wed,sat");
        point5.setArrivalTime(LocalTime.of(19,30));

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);
    }

    @Test
    public void getRoutePos0() {
        List<Schedule> schedule = new ArrayList<>();
        when(trainDao.getRoute(1L)).thenReturn(schedule);
        assertEquals(schedule,trainServiceMock.getRoute(1L));
    }

    @Test
    public void getDepartureTimePos0(){
        String departureTime = "12:12";
        when(trainDao.getDepartureTime(1L, 1L, 1)).thenReturn(departureTime);
        assertEquals(departureTime,trainServiceMock.getDepartureTime(1L, 1L, 1));
    }

    @Test
    public void addTrainPos0(){
        Train train = new Train("1", 1);
        when(trainDao.getTrainByNumber("1")).thenReturn(null);
        trainServiceMock.addTrain(train);
        verify(trainDao).create(train);
    }

    @Test
    public void removeRoutePointPos0() {
        Schedule routePoint = new Schedule();
        RailWayStation station = new RailWayStation("a");
        station.setId(1L);
        routePoint.setStation(station);
        routePoint.setTrain(new Train());
        when(railWayStationService.getStationByTitle("a")).thenReturn(station);
        trainServiceMock.removeRoutePoint(routePoint);
        verify(scheduleService).deleteByStationAndTrainId(1,0);
    }

    @Test
    public void removeTrainPos0() {
        trainServiceMock.removeTrain(1L);
        verify(scheduleService).deleteByTrainId(1L);
    }

    @Test
    public void removeTrainPos1() {
        trainServiceMock.removeTrain(1L);
        verify(trainDao).delete(1L);
    }

    @Test
    public void removeTrainPos2() {
        trainServiceMock.removeTrain(1L);
        verify(passengerService).deleteByTrainId(1L);
    }

    @Test
    public void isExistRoutePointPos0() throws Exception {

        List<Schedule> route = new ArrayList<>();
        RailWayStation checkStation = new RailWayStation("C");

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("A"));
        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("B"));
        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("C"));
        Schedule point4 = new Schedule();
        point4.setStation(new RailWayStation("D"));
        Schedule point5 = new Schedule();
        point5.setStation(new RailWayStation("E"));

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);

        assertTrue(trainService.isExistRoutePoint(route, checkStation));
    }

    @Test
    public void isExistRoutePointNeg0() throws Exception {

        List<Schedule> route = new ArrayList<>();
        RailWayStation checkStation = new RailWayStation("F");

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("A"));
        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("B"));
        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("C"));
        Schedule point4 = new Schedule();
        point4.setStation(new RailWayStation("D"));
        Schedule point5 = new Schedule();
        point5.setStation(new RailWayStation("E"));

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);

        assertFalse(trainService.isExistRoutePoint(route, checkStation));
    }

    @Test
    public void isExistRoutePointNeg1() throws Exception {

        List<Schedule> route = new ArrayList<>();
        RailWayStation checkStation = new RailWayStation();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("A"));
        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("B"));
        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("C"));
        Schedule point4 = new Schedule();
        point4.setStation(new RailWayStation("D"));
        Schedule point5 = new Schedule();
        point5.setStation(new RailWayStation("E"));

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);

        assertFalse(trainService.isExistRoutePoint(route, checkStation));
    }

    @Test
    public void isExistRoutePointNeg3() throws Exception {

        List<Schedule> route = new ArrayList<>();
        RailWayStation checkStation = new RailWayStation(null);

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("A"));
        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("B"));
        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("C"));
        Schedule point4 = new Schedule();
        point4.setStation(new RailWayStation("D"));
        Schedule point5 = new Schedule();
        point5.setStation(new RailWayStation("E"));

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);

        assertFalse(trainService.isExistRoutePoint(route, checkStation));
    }

    @Test
    public void addRoutePointPos0(){
        Schedule routePoint = new Schedule();
        Schedule schedule = new Schedule();
        Train train = new Train();
        train.setId(1L);
        train.setNumber("1");
        train.setTariff(1);
        routePoint.setTrain(train);
        routePoint.setDepartureTime(LocalTime.of(1,1));
        routePoint.setArrivalTime(LocalTime.of(0,0));
        routePoint.setDepartPeriod("sun");
        routePoint.setArrivePeriod("sun");
        RailWayStation station = new RailWayStation();
        station.setId(1L);
        station.setTitle("a");
        routePoint.setStation(station);
        List<Schedule> route = new ArrayList<>();
        when(trainDao.getRoute(1L)).thenReturn(route);
        when(railWayStationService.getStationByTitle("a")).thenReturn(station);
        when(trainDao.read(1L)).thenReturn(train);
        trainServiceMock.addRoutePoint(routePoint);
    }

    @Test
    public void isValidFirstRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = null;
        String[] departDays = {"sun"};
        String[] arriveDays = {""};

        boolean result = TrainServiceImpl.isValidFirstRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidFirstRoutePointNeg1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = null;
        String[] departDays = {"sun"};
        String[] arriveDays = {"sun"};

        boolean result = TrainServiceImpl.isValidFirstRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidFirstRoutePointNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = LocalTime.of(1,30);
        String[] departDays = {"sun"};
        String[] arriveDays = {""};

        boolean result = TrainServiceImpl.isValidFirstRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidFirstRoutePointNeg3(){
        RailWayStation station = new RailWayStation("");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = null;
        String[] departDays = {"sun"};
        String[] arriveDays = {""};

        boolean result = TrainServiceImpl.isValidFirstRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }
    @Test
    public void isValidLastRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(1,30);
        String[] departDays = {""};
        String[] arriveDays = {"sun"};

        boolean result = TrainServiceImpl.isValidLastRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidLastRoutePointNeg1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(1,30);
        String[] departDays = {"sun"};
        String[] arriveDays = {"sun"};

        boolean result = TrainServiceImpl.isValidLastRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidLastRoutePointNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = LocalTime.of(1,30);
        String[] departDays = {""};
        String[] arriveDays = {"sun"};

        boolean result = TrainServiceImpl.isValidLastRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidLastRoutePointNeg3(){
        RailWayStation station = new RailWayStation("");
        LocalTime departDate = LocalTime.of(1,30);
        LocalTime arriveDate = null;
        String[] departDays = {"sun"};
        String[] arriveDays = {""};

        boolean result = TrainServiceImpl.isValidLastRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidPeriodDaysPos0(){
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"mon","tue","fri"};

        boolean result = TrainServiceImpl.isValidPeriodDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidPeriodDaysPos1(){
        String[] departDays = {"mon", "sun"};
        String[] arriveDays = {"sun", "sat"};

        boolean result = TrainServiceImpl.isValidPeriodDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidPeriodDaysPos2(){
        String[] departDays = {"mon", "sun", "fri"};
        String[] arriveDays = {"sun", "sat", "thu"};

        boolean result = TrainServiceImpl.isValidPeriodDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidPeriodDaysNeg0(){
        String[] departDays = {"mon", "sun", "thu"};
        String[] arriveDays = {"sun", "sat", "thu"};

        boolean result = TrainServiceImpl.isValidPeriodDays(departDays, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidRoutePointDaysPos0(){
        String[] departDays = {"sun", "mon", "tue"};
        String[] arriveDays = {"sun", "mon", "tue"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertTrue(result);
    }


    @Test
    public void isValidRoutePointDaysPos1(){
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"sat", "sun"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointDaysPos2(){
        String[] departDays = {"mon", "sun", "tue"};
        String[] arriveDays = {"sun", "sat", "mon"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointDaysPos3(){
        String[] departDays = {"mon", "tue", "wed"};
        String[] arriveDays = {"sun", "mon", "tue"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointDaysNeg0(){
        String[] departDays = {"sun"};
        String[] arriveDays = {"fri"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidRoutePointDaysNeg1(){
        String[] departDays = {"sun", "mon", "wed"};
        String[] arriveDays = {"sun", "mon", "tue"};

        boolean result = TrainServiceImpl.isValidRoutePointDays(departDays, arriveDays);

        assertFalse(result);
    }


    @Test
    public void isValidRoutePointTimePos0(){
        String[] departDays = {"sun, mon"};
        String[] arriveDays = {"sun, mon"};
        LocalTime departTime = LocalTime.of(1,30);
        LocalTime arriveTime = LocalTime.of(1,20);

        boolean result = TrainServiceImpl.isValidRoutePointTime(departDays, arriveDays, departTime, arriveTime);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointTimePos1(){
        String[] departDays = {"mon, mon"};
        String[] arriveDays = {"sun, sun"};
        LocalTime departTime = LocalTime.of(1,30);
        LocalTime arriveTime = LocalTime.of(1,40);

        boolean result = TrainServiceImpl.isValidRoutePointTime(departDays, arriveDays, departTime, arriveTime);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointTimeNeg0(){
        String[] departDays = {"sun, mon"};
        String[] arriveDays = {"sun, mon"};
        LocalTime departTime = LocalTime.of(1,30);
        LocalTime arriveTime = LocalTime.of(1,40);

        boolean result = TrainServiceImpl.isValidRoutePointTime(departDays, arriveDays, departTime, arriveTime);

        assertFalse(result);
    }

    @Test
    public void isValidRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointPos1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,20);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"mon", "tue"};
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointPos2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"sat", "sun"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointPos3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"fri", "sun", "mon"};
        String[] arriveDays = {"thu", "sat", "sun"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointPos4(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,20);
        String[] departDays = {"fri", "sun", "mon"};
        String[] arriveDays = {"fri", "sun", "mon"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isValidRoutePointNeg0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"sat", "mon"};
        String[] arriveDays = {"sun", "sun"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidRoutePointNeg1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidRoutePointNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,30);
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"", ""};

        boolean result = TrainServiceImpl.isValidRoutePoint(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidAddRoutePointDataNeg0(){
        RailWayStation station = new RailWayStation("");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = null;
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = null;

        boolean result = TrainServiceImpl
                .isValidAddRoutePointData(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidAddRoutePointDataNeg1(){
        RailWayStation station = new RailWayStation("");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(14,40);
        String[] departDays = null;
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidAddRoutePointData(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidAddRoutePointDataNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,41);
        String[] departDays = {"sun", "mon"};
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidAddRoutePointData(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isValidAddRoutePointDataNeg3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(14,40);
        LocalTime arriveDate = LocalTime.of(14,41);
        String[] departDays = {"sun"};
        String[] arriveDays = {"sun", "mon"};

        boolean result = TrainServiceImpl.isValidAddRoutePointData(station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddFirstRoutePointPos0(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,10);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays);

        assertTrue(result);
    }

    @Test
    public void isAddFirstRoutePointPos1(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("");

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,10);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays);

        assertTrue(result);
    }

    @Test
    public void isAddFirstRoutePointPos2(){

        List<Schedule> route = new ArrayList<>();

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,10);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays);

        assertTrue(result);
    }

    @Test
    public void isAddFirstRoutePointNeg0(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("");
        point1.setDepartureTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,10);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl
                .isAddFirstRoutePoint(route, station, departDate, departDays);

        assertFalse(result);
    }

    @Test
    public void isAddFirstRoutePointNeg1(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,40);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays);

        assertFalse(result);
    }

    @Test
    public void isAddFirstRoutePointNeg2(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,20);
        LocalTime arriveDate = null;
        String[] departDays = {"tue","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays);

        assertFalse(result);
    }

    @Test
    public void isAddLastRoutePointPos0(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(4,55);
        String[] departDays = {"","",""};
        String[] arriveDays = {"mon","tue","fri"};

        boolean result = TrainServiceImpl
                .isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddLastRoutePointPos1(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("");
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(4,55);
        String[] departDays = {"","",""};
        String[] arriveDays = {"mon","tue","fri"};

        boolean result = TrainServiceImpl
                .isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddLastRoutePointPos2(){

        List<Schedule> route = new ArrayList<>();

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(4,10);
        String[] arriveDays = {"mon","tue","fri"};
        String[] departDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddLastRoutePointNeg0(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setDepartPeriod("");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setArrivePeriod("mon,tue,fri");

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = null;
        LocalTime arriveDate = LocalTime.of(4,40);
        String[] arriveDays = {"mon","tue","fri"};
        String[] departDays = {"", "", ""};

        boolean result = TrainServiceImpl
                .isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddLastRoutePointNeg1(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime arriveDate = LocalTime.of(4,40);
        LocalTime departDate = null;
        String[] arriveDays = {"mon","tue","fri"};
        String[] departDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddLastRoutePointNeg2(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("station"));
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(4,50));

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime arriveDate = LocalTime.of(4,55);
        LocalTime departDate = null;
        String[] arriveDays = {"tue","tue","fri"};
        String[] departDays = {"", "", ""};

        boolean result = TrainServiceImpl
                .isAddLastRoutePoint(route, station, arriveDate, arriveDays);

        assertFalse(result);
    }

    @Test
    public void getNextRoutePoint0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(8,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(8,30);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(route.get(2), TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getNextRoutePoint1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(8,40);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(8,30);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(route.get(3), TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test(expected = TrainRoutePointAddException.class)
    public void getNextRoutePoint2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(1,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(8,30);
        String[] arriveDays = {"tue", "wed", "sat"};

        TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);
    }

    @Test
    public void getNextRoutePoint3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(18,10);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(18,5);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(route.get(4), TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getNextRoutePoint4(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(23,40);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(23,20);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(null, TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getNextRoutePoint5(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(0,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(0,30);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(null, TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test (expected = TrainRoutePointAddException.class)
    public void getNextRoutePoint6(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(4,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(3,0);
        String[] arriveDays = {"mon", "tue", "fri"};

        TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);
    }

    @Test(expected = TrainRoutePointAddException.class)
    public void getNextRoutePoint7(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(4,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(3,35);
        String[] arriveDays = {"mon", "tue", "fri"};

        TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);
    }

    @Test
    public void getNextRoutePoint8(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(route.get(1), TrainServiceImpl
                .getNextRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(8,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(8,30);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(route.get(1), TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointPos1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(9,50);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(9,45);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(route.get(3), TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointPos2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,20);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,10);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(route.get(0), TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointPos3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(12,20);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(12,10);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(route.get(3), TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointPos4(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(22,20);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(22,10);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(null, TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test(expected = TrainRoutePointAddException.class)
    public void getPrevRoutePointNeg0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(5,0);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(4,40);
        String[] arriveDays = {"mon", "tue", "fri"};

        TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);
    }

    @Test(expected = TrainRoutePointAddException.class)
    public void getPrevRoutePointNeg1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(4,45);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(4,40);
        String[] arriveDays = {"mon", "tue", "fri"};

        TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);
    }

    @Test
    public void getPrevRoutePointNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(20,0);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(19,0);
        String[] arriveDays = {"tue", "wed", "sat"};

        assertEquals(null, TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void getPrevRoutePointNeg3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        assertEquals(route.get(0), TrainServiceImpl
                .getPrevRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays));
    }

    @Test
    public void isAddMiddleRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,21);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        //dsa
        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointPos1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddMiddleRoutePointPos2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(18,30);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(18,20);
        String[] arriveDays = {"tue", "wed", "sat"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddMiddleRoutePointPos3(){
        List<Schedule> route1 = new ArrayList<>();
        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("a"));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(1,30));
        route1.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route1, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddMiddleRoutePointPos4(){
        List<Schedule> route1 = new ArrayList<>();
        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("a"));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("b"));
        point2.setArrivePeriod("mon,tue,fri");
        point2.setArrivalTime(LocalTime.of(4,30));
        point2.setDepartPeriod("mon,tue,fri");
        point2.setDepartureTime(LocalTime.of(4,50));

        route1.add(point1);
        route1.add(point2);

        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(5,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(4,55);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route1, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddMiddleRoutePointPos5(){
        List<Schedule> route1 = new ArrayList<>();
        Schedule point1 = new Schedule();
        point1.setStation(new RailWayStation("a"));
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setStation(new RailWayStation("b"));
        point2.setArrivePeriod("mon,tue,fri");
        point2.setArrivalTime(LocalTime.of(4,30));
        point2.setDepartPeriod("mon,tue,fri");
        point2.setDepartureTime(LocalTime.of(4,50));

        Schedule point3 = new Schedule();
        point3.setStation(new RailWayStation("c"));
        point3.setArrivePeriod("mon,tue,fri");
        point3.setArrivalTime(LocalTime.of(12,50));
        point3.setDepartPeriod("mon,tue,fri");
        point3.setDepartureTime(LocalTime.of(13,15));

        route1.add(point1);
        route1.add(point2);
        route1.add(point3);

        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(15,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(14,55);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route1, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }


    @Test
    public void isAddMiddleRoutePointNeg0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(20,30);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(20,20);
        String[] arriveDays = {"tue", "wed", "sat"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointNeg1(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(0,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(0,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointNeg2(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(3,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(0,20);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointNeg3(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(4,40);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,0);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointNeg4(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(4,40);
        String[] departDays = {"tue", "wed", "thu"};
        LocalTime arriveTime = LocalTime.of(4,0);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddMiddleRoutePointNeg5(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(20,0);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(19,0);
        String[] arriveDays = {"tue", "wed", "sat"};

        boolean result = TrainServiceImpl
                .isAddMiddleRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void isAddRoutePointPos0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(2,30);
        String[] departDays = {"mon", "tue", "fri"};
        LocalTime arriveTime = LocalTime.of(2,21);
        String[] arriveDays = {"mon", "tue", "fri"};

        boolean result = TrainServiceImpl
                .isAddRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddRoutePointNeg0(){
        RailWayStation station = new RailWayStation("station");
        LocalTime departTime = LocalTime.of(20,0);
        String[] departDays = {"tue", "wed", "sat"};
        LocalTime arriveTime = LocalTime.of(19,0);
        String[] arriveDays = {"tue", "wed", "sat"};

        boolean result = TrainServiceImpl
                .isAddRoutePoint(route, station, departTime, departDays, arriveTime, arriveDays);

        assertFalse(result);
    }

    @Test
    public void parseToIntTrainPeriodPos0(){
        String[] days = {"sun", "mon", "thu"};
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(2);
        result.add(5);

        assertEquals(result, TrainServiceImpl.parseToIntTrainPeriod(days));
    }

    @Test
    public void parseToIntTrainPeriodPos1(){
        String[] days = {""};
        List<Integer> result = new ArrayList<>();
        result.add(0);

        assertEquals(result, TrainServiceImpl.parseToIntTrainPeriod(days));
    }
}