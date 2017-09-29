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
        trainService.create(train);
        Train t = trainService.read(1);

        RailWayStation s1 = railWayStationService.read(1);
        Schedule sh1 = new Schedule(LocalTime.of(11,30), LocalTime.of(11,30), 1,1,92);
        sh1.setTrain(t);
        sh1.setStation(s1);
        scheduleService.create(sh1);

        RailWayStation s2 = railWayStationService.read(2);
        Schedule sh2 = new Schedule(LocalTime.of(12,30), LocalTime.of(12,30), 1,1,92);
        sh2.setTrain(t);
        sh2.setStation(s2);
        scheduleService.create(sh2);

        RailWayStation s3 = railWayStationService.read(3);
        Schedule sh3 = new Schedule(LocalTime.of(13,30), LocalTime.of(13,30), 1,1,92);
        sh3.setTrain(t);
        sh3.setStation(s3);
        scheduleService.create(sh3);

        Train t1 = trainService.read(2);

        RailWayStation s4 = railWayStationService.read(1);
        Schedule sh4 = new Schedule(LocalTime.of(11,30), LocalTime.of(11,30), 1,1,92);
        sh1.setTrain(t1);
        sh1.setStation(s4);
        scheduleService.create(sh4);

        RailWayStation s5 = railWayStationService.read(2);
        Schedule sh5 = new Schedule(LocalTime.of(12,30), LocalTime.of(12,30), 1,1,92);
        sh5.setTrain(t1);
        sh5.setStation(s5);
        scheduleService.create(sh5);

        RailWayStation s6 = railWayStationService.read(3);
        Schedule sh6 = new Schedule(LocalTime.of(13,30), LocalTime.of(13,30), 1,1,92);
        sh6.setTrain(t1);
        sh6.setStation(s6);
        scheduleService.create(sh6);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/getTrain/{id}",method = RequestMethod.GET)
    public ResponseEntity<Train> addTrain(@PathVariable("id") int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/deleteTrain/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteTrain(@PathVariable("id") int id){
        scheduleService.searchTrain(2,3,3);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value="/updateTrain",method = RequestMethod.PUT)
    public void updateTrain(@RequestBody Train train) {
        trainService.update(train);
    }

}
