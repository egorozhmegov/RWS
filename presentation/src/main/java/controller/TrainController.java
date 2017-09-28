package controller;

import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;
import service.interfaces.TrainService;
import java.time.LocalTime;
import java.util.List;

@RestController
public class TrainController {

    private final static Logger LOG = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    private TrainService trainService;

    @Autowired
    private RailWayStationService railWayStationService;

    @Autowired
    private ScheduleService scheduleService;


    @RequestMapping(value="/getTrains",method = RequestMethod.GET)
    public List<Train> getAllTrains() {
        return trainService.getAll();
    }

    @RequestMapping(value="/addTrain",method = RequestMethod.POST)
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        //TODO
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/getTrain/{id}",method = RequestMethod.GET)
    public ResponseEntity<Train> addTrain(@PathVariable("id") int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/deleteTrain/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteTrain(@PathVariable("id") int id){
        scheduleService.deleteByTrain(trainService.read(id).getNumber());
        trainService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value="/updateTrain",method = RequestMethod.PUT)
    public void updateTrain(@RequestBody Train train) {
        trainService.update(train);
    }

}
