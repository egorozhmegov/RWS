package controller;

import exception.RailWayStationServiceException;
import model.RailWayStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.RailWayStationService;

import java.util.List;

@RestController
public class RailWayStationController {

    @Autowired
    private RailWayStationService railWayStationService;

    @RequestMapping(value = "/getStations", method = RequestMethod.GET)
    public ResponseEntity<List<RailWayStation>> getStations() {
        return new ResponseEntity<>(railWayStationService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addStation", method = RequestMethod.POST)
    public ResponseEntity<RailWayStation> addStation(@RequestBody RailWayStation station) {
        try {
            railWayStationService.addStation(station);
            return new ResponseEntity<>(station, HttpStatus.CREATED);
        } catch (RailWayStationServiceException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/deleteStation/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteStation(@PathVariable("id") long id) {
        railWayStationService.removeStation(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
