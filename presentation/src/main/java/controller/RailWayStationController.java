package controller;

import exception.RailWayStationServiceException;
import model.RailWayStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.RailWayStationService;

import java.util.List;

/**
 *Railway station rest controller.
 */
@RestController
public class RailWayStationController {

    @Autowired
    public RailWayStationController(RailWayStationService railWayStationService) {
        this.railWayStationService = railWayStationService;
    }

    private final RailWayStationService railWayStationService;

    /**
     * Get all stations.
     *
     * @return ResponseEntity<List<RailWayStation>>
     */
    @RequestMapping(value="/getStations",method = RequestMethod.GET)
    public ResponseEntity<List<RailWayStation>> getStations() {
        return new ResponseEntity<>(railWayStationService.getAll(), HttpStatus.OK);
    }

    /**
     * Add new station.
     *
     * @param station RailWayStation
     * @return ResponseEntity<RailWayStation>
     */
    @RequestMapping(value="/addStation",method = RequestMethod.POST)
    public ResponseEntity<RailWayStation> addStation(@RequestBody RailWayStation station) {
        try{
            railWayStationService.addStation(station);
            return new ResponseEntity<>(station, HttpStatus.CREATED);
        } catch (RailWayStationServiceException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Delete station by id.
     *
     * @param id long
     * @return ResponseEntity<Long>
     */
    @RequestMapping(value="/deleteStation/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteStation(@PathVariable("id") long id){
        railWayStationService.removeStation(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
