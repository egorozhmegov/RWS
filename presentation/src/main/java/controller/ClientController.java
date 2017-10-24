package controller;

import model.RailWayStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.ClientService;
import util.ScheduleWrapper;
import util.StationWrapper;

import java.util.List;

/**
 * Rest client controller. Use for bind client API with server. Data transport by json.
 */
@RestController
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Get all stations.
     *
     * @return ResponseEntity<List<RailWayStation>>
     */
    @RequestMapping(value="/client/getStations",method = RequestMethod.GET)
    public ResponseEntity<List<RailWayStation>> getStations() {
        return new ResponseEntity<>(clientService.getAllStations(), HttpStatus.OK);
    }

    /**
     * Get all arrival and departure trains by station title and date,
     * Using station and schedule wrappers.
     *
     * @param station StationWrapper station
     * @return ResponseEntity<ScheduleWrapper>
     */
    @RequestMapping(value="/client/getSchedule",method = RequestMethod.POST)
    public ResponseEntity<ScheduleWrapper> getSchedule(@RequestBody StationWrapper station) {
        ScheduleWrapper schedule = clientService.getSchedule(station);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

}
