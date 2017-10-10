package controller;

import exception.TrainServiceException;
import model.Schedule;
import model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.TrainService;
import java.util.List;

/**
 * Rest train controller. Use for bind client API with server. Data transport by json.
 */
@RestController
public class TrainController {

    @Autowired
    private TrainService trainService;


    @RequestMapping(value="/getTrains",method = RequestMethod.GET)
    public ResponseEntity<List<Train>> getAllTrains() {
        return new ResponseEntity<>(trainService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/addTrain",method = RequestMethod.POST)
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        try{
            trainService.addTrain(train);
            return new ResponseEntity<>(train, HttpStatus.CREATED);
        } catch (TrainServiceException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="/deleteTrain/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteTrain(@PathVariable("id") long id){
        trainService.removeTrain(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value="/getRoute/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Schedule>> getRoute(@PathVariable("id") long id){
       return new ResponseEntity<>(trainService.getRoute(id), HttpStatus.OK);
    }

    @RequestMapping(value="/addRoutePoint",method = RequestMethod.POST)
    public ResponseEntity<Schedule> addRoutePoint(@RequestBody Schedule routePoint){
        try{
            trainService.addRoutePoint(routePoint);
            return new ResponseEntity<>(routePoint, HttpStatus.CREATED);
        } catch (TrainServiceException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="/deleteRoutePoint",method = RequestMethod.POST)
    public ResponseEntity<Schedule> deleteRoutePoint(@RequestBody Schedule routePoint){
        trainService.removeRoutePoint(routePoint);
        return new ResponseEntity<>(routePoint, HttpStatus.OK);
    }
}
