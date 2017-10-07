package service.implementation;

import model.RailWayStation;
import model.Schedule;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrainServiceImplTest {

    private TrainServiceImpl trainService = new TrainServiceImpl();

    private List<Schedule> getRoute1(){
        List<Schedule> route = new ArrayList<>();

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

        route.add(point1);
        route.add(point2);
        route.add(point3);
        route.add(point4);
        route.add(point5);

        return route;
    }

    private List<Schedule> getRoute2(){
        List<Schedule> route = new ArrayList<>();

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

        route.add(point1);
        route.add(point2);
        route.add(point3);

        return route;
    }

    private List<Schedule> getRoute3(){
        List<Schedule> route = new ArrayList<>();

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

        route.add(point1);
        route.add(point2);
        route.add(point3);

        return route;
    }

    private List<Schedule> getRoute4(){
        List<Schedule> route = new ArrayList<>();

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

        route.add(point1);
        route.add(point2);
        route.add(point3);

        return route;
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
    public void isValidArriveAndDepartTimes0(){
        Schedule schedule = new Schedule();
        schedule.setArrivePeriod("sun,mon,thu");
        schedule.setDepartPeriod("sun,mon,thu");
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));

        assertTrue(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes1(){
        Schedule schedule = new Schedule();
        schedule.setArrivePeriod("sun,mon,thu");
        schedule.setDepartPeriod("sun,mon,thu");
        schedule.setArrivalTime(LocalTime.of(13, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));

        assertFalse(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes2(){
        Schedule schedule = new Schedule();
        schedule.setArrivePeriod("mon,thu");
        schedule.setDepartPeriod("sun,mon");
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));

        assertFalse(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes3(){
        Schedule schedule = new Schedule();
        schedule.setArrivalTime(LocalTime.of(12, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));
        schedule.setArrivePeriod("sun,mon");
        schedule.setDepartPeriod("mon,thu");

        assertTrue(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes4(){
        Schedule schedule = new Schedule();
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));
        schedule.setArrivePeriod("sat");
        schedule.setDepartPeriod("sun");

        assertTrue(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes5(){
        Schedule schedule = new Schedule();
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));
        schedule.setArrivePeriod("sun,mon");
        schedule.setDepartPeriod("mon,sun");

        assertFalse(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes6(){
        Schedule schedule = new Schedule();
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));
        schedule.setArrivePeriod("");
        schedule.setDepartPeriod("mon,sun");

        assertTrue(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isValidArriveAndDepartTimes7(){
        Schedule schedule = new Schedule();
        schedule.setArrivalTime(LocalTime.of(10, 30));
        schedule.setDepartureTime(LocalTime.of(11, 30));
        schedule.setArrivePeriod("mon,sun");
        schedule.setDepartPeriod("");

        assertTrue(trainService.isValidArriveAndDepartTimes(schedule));
    }

    @Test
    public void isPossibleAddRoutePoint0(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(0,30));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint1(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(5,30));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint2(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint3(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(19,20));
        routePoint.setDepartPeriod("");

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint4(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("tue,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(0,30));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint5(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,fri");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
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

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint10(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(7,30));
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(7,35));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint11(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(14,30));
        routePoint.setDepartPeriod("tue,wed,sat");
        routePoint.setDepartureTime(LocalTime.of(5,50));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint12(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("tue,wed,sat");
        routePoint.setArrivalTime(LocalTime.of(14,30));
        routePoint.setDepartPeriod("tue,wed,sat");
        routePoint.setDepartureTime(LocalTime.of(15,15));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint13(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(1,15));
        routePoint.setDepartPeriod("mon,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(2,35));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint14(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("mon,tue,fri");
        routePoint.setArrivalTime(LocalTime.of(2,15));
        routePoint.setDepartPeriod("tue,tue,fri");
        routePoint.setDepartureTime(LocalTime.of(2,35));

        assertFalse(trainService.isPossibleAddRoutePoint(routePoint, getRoute1()));
    }

    @Test
    public void isPossibleAddRoutePoint15(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("");
        routePoint.setDepartPeriod("fri,sat,sun");
        routePoint.setDepartureTime(LocalTime.of(5,30));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute2()));
    }

    @Test
    public void isPossibleAddRoutePoint17(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("sat,sun,mon");
        routePoint.setArrivalTime(LocalTime.of(19,50));
        routePoint.setDepartPeriod("");

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute3()));
    }

    @Test
    public void isPossibleAddRoutePoint18(){
        Schedule routePoint = new Schedule();

        routePoint.setArrivePeriod("sat,sun,mon");
        routePoint.setArrivalTime(LocalTime.of(7,15));
        routePoint.setDepartPeriod("sat,sun,mon");
        routePoint.setDepartureTime(LocalTime.of(7,35));

        assertTrue(trainService.isPossibleAddRoutePoint(routePoint, getRoute4()));
    }

}