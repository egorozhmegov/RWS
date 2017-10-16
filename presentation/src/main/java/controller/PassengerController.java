package controller;

import model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.PassengerService;

import java.util.List;

@RestController
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(value="/getPassengers",method = RequestMethod.GET)
    public ResponseEntity<List<Passenger>> getStations() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }
}
