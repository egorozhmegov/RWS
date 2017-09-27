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
        if(trainService.existTrain(train.getNumber())){
            LOG.info(String.format("Train with number: %s exist already.", train.getNumber()));
            return new ResponseEntity<Train>(train, HttpStatus.NO_CONTENT);
        } else {
//            railWayStationService.create(new RailWayStation("Znamesk"));
//            railWayStationService.create(new RailWayStation("Smolensk"));
//            railWayStationService.create(new RailWayStation("Volgograd"));
            Train train2 = trainService.read(4);
            RailWayStation s1 = railWayStationService.read(1);
            Schedule schedule1 = new Schedule(LocalTime.of(12, 30));
            schedule1.setTrain(train2);
            schedule1.setStation(s1);
            scheduleService.create(schedule1);
            s1.getSchedule().add(schedule1);
            railWayStationService.update(s1);
            train2.getRoute().add(s1);


            RailWayStation s2 = railWayStationService.read(2);
            Schedule schedule2 = new Schedule(LocalTime.of(13, 30));
            schedule1.setTrain(train2);
            schedule1.setStation(s2);
            scheduleService.create(schedule2);
            s2.getSchedule().add(schedule2);
            railWayStationService.update(s2);
            train2.getRoute().add(s2);

            RailWayStation s3 = railWayStationService.read(3);
            Schedule schedule3 = new Schedule(LocalTime.of(14, 30));
            schedule3.setTrain(train2);
            schedule3.setStation(s3);
            scheduleService.create(schedule3);
            s3.getSchedule().add(schedule3);
            railWayStationService.update(s3);
            train2.getRoute().add(s3);

            trainService.create(train);

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
