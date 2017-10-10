package service.implementation;

import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

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
}