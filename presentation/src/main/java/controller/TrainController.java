package controller;

import model.Passenger;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.PassengerService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;
import service.interfaces.TrainService;

import java.time.LocalDate;
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

    @Autowired
    private PassengerService passengerService;


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
        //TODO
        Passenger p1 = new Passenger("Egor", "Ozhmegov", "1991-12-02");
        passengerService.create(p1);
        p1.setTrain(trainService.read(1));
        p1.setStation(railWayStationService.read(1));
        p1.setTrainDate("2017-10-25");
        passengerService.update(p1);

        Passenger p2 = new Passenger("Egor", "Ozhmegov", "1991-12-02");
        passengerService.create(p2);
        p2.setTrain(trainService.read(1));
        p2.setStation(railWayStationService.read(2));
        p2.setTrainDate("2017-10-26");
        passengerService.update(p2);

        Passenger p3 = new Passenger("Egor", "Ozhmegov", "1991-12-02");
        passengerService.create(p3);
        p3.setTrain(trainService.read(1));
        p3.setStation(railWayStationService.read(3));
        p3.setTrainDate("2017-10-27");
        passengerService.update(p3);

        Passenger p4 = new Passenger("Egor", "Egorov", "1991-12-02");
        passengerService.create(p4);
        p4.setTrain(trainService.read(1));
        p4.setStation(railWayStationService.read(3));
        p4.setTrainDate("2017-10-25");
        passengerService.update(p4);

        Passenger p5 = new Passenger("Egor", "Egorov", "1991-12-02");
        passengerService.create(p5);
        p5.setTrain(trainService.read(1));
        p5.setStation(railWayStationService.read(3));
        p5.setTrainDate("2017-10-26");
        passengerService.update(p5);

        Passenger p6 = new Passenger("Egor", "Egorov", "1991-12-02");
        passengerService.create(p6);
        p6.setTrain(trainService.read(1));
        p6.setStation(railWayStationService.read(3));
        p6.setTrainDate("2017-10-27");
        passengerService.update(p6);


        passengerService.getRegisteredPassenger(1,2,4, "2017-10-26");

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value="/updateTrain",method = RequestMethod.PUT)
    public void updateTrain(@RequestBody Train train) {
        trainService.update(train);
    }

}
