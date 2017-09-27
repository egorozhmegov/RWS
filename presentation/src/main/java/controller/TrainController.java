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
            railWayStationService.create(new RailWayStation("Znamesk"));
            railWayStationService.create(new RailWayStation("Smolensk"));
            railWayStationService.create(new RailWayStation("Volgograd"));
            RailWayStation s1 = railWayStationService.read(1);
            RailWayStation s2 = railWayStationService.read(2);
            RailWayStation s3 = railWayStationService.read(3);

            trainService.create(train);

            Train train2 = trainService.read(1);

            train2.getRoute().add(s1);
            train2.getRoute().add(s2);
            train2.getRoute().add(s3);

            trainService.update(train2);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/getTrain/{trainId}",method = RequestMethod.GET)
    public ResponseEntity<Train> addTrain(@PathVariable("trainId") int trainId) {
        return new ResponseEntity<Train>(HttpStatus.OK);
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
