package controller;

import model.RailWayStation;
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
        if(trainService.existTrain(train.getTrainNumber())){
            LOG.info(String.format("Train with number: %s exist already.", train.getTrainNumber()));
            return new ResponseEntity<Train>(train, HttpStatus.NO_CONTENT);
        } else {
            LOG.info(String.format("Created train with number: %s", train.getTrainNumber()));
            railWayStationService.create(new RailWayStation("A"));
            railWayStationService.create(new RailWayStation("B"));
            railWayStationService.create(new RailWayStation("C"));
            RailWayStation s1 = railWayStationService.read(1);
            System.out.println(s1);
            RailWayStation s2 = railWayStationService.read(2);
            RailWayStation s3 = railWayStationService.read(3);
            train.getRoute().add(s1);
            train.getRoute().add(s2);
            train.getRoute().add(s3);
            trainService.create(train);
            return new ResponseEntity<Train>(train, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/deleteTrain/{trainId}",method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteTrain(@PathVariable("trainId") int trainId){
        trainService.delete(trainId);
        return new ResponseEntity<Integer>(trainId, HttpStatus.OK);
    }

    @RequestMapping(value="/updateTrain",method = RequestMethod.PUT)
    public void updateTrain(@RequestBody Train train) {
        trainService.update(train);
    }

}
