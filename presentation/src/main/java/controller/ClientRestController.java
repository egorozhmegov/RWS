package controller;

import model.Passenger;
import model.RailWayStation;
import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.ClientService;
import util.ScheduleWrapper;
import util.StationWrapper;

import java.time.LocalDate;
import java.util.List;

/**
 * Rest client controller. Use for bind client API with server. Data transport by json.
 */
@RestController
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value="/client/getStations",method = RequestMethod.GET)
    public ResponseEntity<List<RailWayStation>> getStations() {
        return new ResponseEntity<>(clientService.getAllStations(), HttpStatus.OK);
    }

    @RequestMapping(value="/client/getSchedule",method = RequestMethod.POST)
    public ResponseEntity<ScheduleWrapper> getSchedule(@RequestBody StationWrapper station) {
        System.out.println(station);
        ScheduleWrapper schedule = clientService.getSchedule("", "");
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
    
}
