package service.implementation;

import dao.interfaces.TrainDao;
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
    private List<Schedule> route1 = new ArrayList<>();
    private List<Schedule> route2 = new ArrayList<>();
    private List<Schedule> route3 = new ArrayList<>();
    private List<Schedule> route4 = new ArrayList<>();

    @Before
    public void initRoute1(){
        Schedule point1 = new Schedule();
        point1.setDepartPeriod("mon,tue,fri");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setArrivePeriod("mon,tue,fri");
        point2.setArrivalTime(LocalTime.of(4,30));
        point2.setDepartPeriod("mon,tue,fri");
        point2.setDepartureTime(LocalTime.of(4,50));

        Schedule point3 = new Schedule();
        point3.setArrivePeriod("mon,tue,fri");
        point3.setArrivalTime(LocalTime.of(12,50));
        point3.setDepartPeriod("mon,tue,fri");
        point3.setDepartureTime(LocalTime.of(13,15));

        Schedule point4 = new Schedule();
        point4.setArrivePeriod("tue,wed,sat");
        point4.setArrivalTime(LocalTime.of(8,50));
        point4.setDepartPeriod("tue,wed,sat");
        point4.setDepartureTime(LocalTime.of(9,30));

        Schedule point5 = new Schedule();
        point5.setArrivePeriod("tue,wed,sat");
        point5.setArrivalTime(LocalTime.of(19,30));

        route1.add(point1);
        route1.add(point2);
        route1.add(point3);
        route1.add(point4);
        route1.add(point5);
    }


    @Before
    public void initRoute2(){
        Schedule point1 = new Schedule();
        point1.setDepartPeriod("sat,sun,mon");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setArrivePeriod("sun,mon,tue");
        point2.setArrivalTime(LocalTime.of(4,30));
        point2.setDepartPeriod("sun,mon,tue");
        point2.setDepartureTime(LocalTime.of(4,50));

        Schedule point3 = new Schedule();
        point3.setArrivePeriod("mon,tue,wed");
        point3.setArrivalTime(LocalTime.of(19,30));

        route2.add(point1);
        route2.add(point2);
        route2.add(point3);
    }

    @Before
    public void initRoute3(){
        Schedule point1 = new Schedule();
        point1.setDepartPeriod("fri,sat,sun");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setArrivePeriod("fri,sat,sun");
        point2.setArrivalTime(LocalTime.of(12,30));
        point2.setDepartPeriod("fri,sat,sun");
        point2.setDepartureTime(LocalTime.of(12,50));

        Schedule point3 = new Schedule();
        point3.setArrivePeriod("fri,sat,sun");
        point3.setArrivalTime(LocalTime.of(19,30));

        route3.add(point1);
        route3.add(point2);
        route3.add(point3);
    }

    @Before
    public void initRoute4(){
        Schedule point1 = new Schedule();
        point1.setDepartPeriod("fri,sat,sun");
        point1.setDepartureTime(LocalTime.of(1,30));

        Schedule point2 = new Schedule();
        point2.setArrivePeriod("sat,sun,mon");
        point2.setArrivalTime(LocalTime.of(12,30));
        point2.setDepartPeriod("sat,sun,mon");
        point2.setDepartureTime(LocalTime.of(12,50));

        Schedule point3 = new Schedule();
        point3.setArrivePeriod("sun,mon,tue");
        point3.setArrivalTime(LocalTime.of(19,30));

        route4.add(point1);
        route4.add(point2);
        route4.add(point3);
    }

    @Test
    public void getRoute0() {
        List<Schedule> schedule = new ArrayList<>();
        when(trainDao.getRoute(1L)).thenReturn(schedule);
        assertEquals(schedule,trainServiceMock.getRoute(1L));
    }

    @Test
    public void getDepartureTime0(){
        String departureTime = "12:12";
        when(trainDao.getDepartureTime(1L, 1L, 1)).thenReturn(departureTime);
        assertEquals(departureTime,trainServiceMock.getDepartureTime(1L, 1L, 1));
    }

    @Test
    public void addTrain0(){
        Train train = new Train("1", 1);
        when(trainDao.getTrainByNumber("1")).thenReturn(null);
        trainServiceMock.addTrain(train);
        verify(trainDao).create(train);
    }

    @Test
    public void removeRoutePoint0() {
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
    public void removeTrain0() {
        trainServiceMock.removeTrain(1L);
        verify(scheduleService).deleteByTrainId(1L);
    }

    @Test
    public void removeTrain1() {
        trainServiceMock.removeTrain(1L);
        verify(trainDao).delete(1L);
    }

    @Test
    public void removeTrain2() {
        trainServiceMock.removeTrain(1L);
        verify(passengerService).deleteByTrainId(1L);
    }

    @Test
    public void isExistRoutePoint0() throws Exception {

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
    public void isExistRoutePoint1() throws Exception {

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
    public void isExistRoutePoint2() throws Exception {

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
    public void isExistRoutePoint3() throws Exception {

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
    public void parseToIntTrainPeriod0(){
        String[] days = {"sun", "mon", "thu"};
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(2);
        result.add(5);

        assertEquals(result, trainService.parseToIntTrainPeriod(days));
    }

    @Test
    public void parseToIntTrainPeriod1(){
        String[] days = {""};
        List<Integer> result = new ArrayList<>();
        result.add(0);

        assertEquals(result, trainService.parseToIntTrainPeriod(days));
    }

    @Test
    public void isPossibleAddRoutePoint1(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(5,30));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint2(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint3(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(19,20));
        routePoint.setDepartPeriod("");

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint4(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("tue,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(0,30));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint5(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,fri");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint6(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,fri");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, new ArrayList<>()));
    }

    @Test
    public void isPossibleAddRoutePoint7(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartureTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("tue,wed,fri");

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, new ArrayList<>()));
    }

    @Test
    public void isPossibleAddRoutePoint8(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,fri");
        routePoint.setArrivalTime(LocalTime.of(19,30));
        routePoint.setDepartPeriod("tue,wed,fri");
        routePoint.setDepartureTime(LocalTime.of(19,50));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, new ArrayList<>()));
    }

    @Test
    public void isPossibleAddRoutePoint9(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(2,30));
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(2,35));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint10(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(7,30));
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(7,35));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint11(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(14,30));
        routePoint.setDepartPeriod("tue,wed,sat");
        routePoint.setDepartureTime(LocalTime.of(5,50));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint12(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(14,30));
        routePoint.setDepartPeriod("tue,wed,sat");
        routePoint.setDepartureTime(LocalTime.of(15,15));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint13(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(1,15));
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(2,35));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint14(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(2,15));
        routePoint.setDepartPeriod("tue,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(2,35));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, route1));
    }

    @Test
    public void isPossibleAddRoutePoint15(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("fri,sat,sun");
        routePoint.setDepartureTime(LocalTime.of(5,30));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route2));
    }

    @Test
    public void isPossibleAddRoutePoint17(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("sat,sun,mon");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint,route3));
    }

    @Test
    public void isPossibleAddRoutePoint18(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("sat,sun,mon");
        routePoint.setArrivalTime(LocalTime.of(7,15));
        routePoint.setDepartPeriod("sat,sun,mon");
        routePoint.setDepartureTime(LocalTime.of(7,35));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, route4));
    }

    @Test
    public void addRoutePoint(){
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
        LocalTime arriveDate = null;
        String[] departDays = {"fri", "sun", "mon"};
        String[] arriveDays = null;

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

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

        assertTrue(result);
    }

    @Test
    public void isAddFirstRoutePointPos1(){

        List<Schedule> route = new ArrayList<>();

        Schedule point1 = new Schedule();
        point1.setArrivePeriod("mon,tue,fri");
        point1.setArrivalTime(LocalTime.of(4,30));
        point1.setDepartPeriod("");

        route.add(point1);

        RailWayStation station = new RailWayStation("station");
        LocalTime departDate = LocalTime.of(4,10);
        LocalTime arriveDate = null;
        String[] departDays = {"mon","tue","fri"};
        String[] arriveDays = {"", "", ""};

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

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

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

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

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

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

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

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

        boolean result = TrainServiceImpl.isAddFirstRoutePoint(route, station, departDate, departDays, arriveDate, arriveDays);

        assertFalse(result);
    }
}