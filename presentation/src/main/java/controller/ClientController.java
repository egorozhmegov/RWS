package controller;

import exception.*;
import model.RailWayStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.ClientService;
import util.*;

import java.util.List;

/**
 * Rest client controller. Use for bind client API with server. Data transport by json.
 */
@RestController
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Get all stations.
     *
     * @return ResponseEntity<List<RailWayStation>>
     */
    @RequestMapping(value="/client/getStations",method = RequestMethod.GET)
    public ResponseEntity<List<RailWayStation>> getStations() {
        return new ResponseEntity<>(clientService.getAllStations(), HttpStatus.OK);
    }

    /**
     * Get all arrival and departure trains by station title and date,
     * Using station and schedule wrappers.
     *
     * @param station StationWrapper station
     * @return ResponseEntity<ScheduleWrapper>
     */
    @RequestMapping(value="/client/getSchedule",method = RequestMethod.POST)
    public ResponseEntity<ScheduleWrapper> getSchedule(@RequestBody StationWrapper station) {
        return new ResponseEntity<>(clientService.getSchedule(station), HttpStatus.OK);
    }

    /**
     * Get all trains by selected date and stations,
     * Using search request wrapper.
     *
     * @param request SearchTrain
     * @return ResponseEntity<List<TrainWrapper>>
     */
    @RequestMapping(value="/client/searchTrains",method = RequestMethod.POST)
    public ResponseEntity<List<TrainWrapper>> searchTrains(@RequestBody TrainWrapper request) {
        try{
            return new ResponseEntity<>(clientService.searchTrains(request), HttpStatus.OK);
        } catch (ClientServiceException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch(ServiceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Send ticket data on in service method.
     *
     * @param ticketData TicketData
     * @return ResponseEntity<TicketData>
     */
    @RequestMapping(value="/client/payment",method = RequestMethod.POST)
    public ResponseEntity<TicketData> payment(@RequestBody TicketData ticketData) {
        try{
            clientService.buyTicket(ticketData);
            return new ResponseEntity<>(ticketData, HttpStatus.OK);
        } catch (ClientServiceException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ClientServiceRegisteredPassengerException e){
            return new ResponseEntity<>(HttpStatus.FOUND);
        } catch (ClientServiceTimeOutException | ClientServiceNoSeatsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ClientServiceEmailException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
