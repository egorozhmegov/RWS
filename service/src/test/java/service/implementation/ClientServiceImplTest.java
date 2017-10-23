package service.implementation;

import model.Schedule;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClientServiceImplTest {

    private ClientServiceImpl clientService = new ClientServiceImpl();

    @Test
    public void parseDate0(){
        String date = "10/09/2017";

        assertEquals(LocalDate.of(
                2017, 10, 9)
                , clientService.parseDate(date));
    }

    @Test(expected = NullPointerException.class)
    public void parseDate1(){
        clientService.parseDate(null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void parseDate2(){
        clientService.parseDate("");
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

    @Test
    public void parseDashDate0(){
        assertEquals(LocalDate.of(2017, 10, 15),
                clientService.parseDashDate("2017-10-15"));
    }
}