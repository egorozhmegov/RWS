package controller;

import exception.ServiceMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.TimetableService;
import util.TimetableMessage;

/**
 *Timetable rest controller.
 */
@RestController
public class TimetableController {

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    private TimetableService timetableService;

    /**
     * Send message on table.
     *
     * @param message TimetableMessage
     * @return ResponseEntity<TimetableMessage>
     */
    @RequestMapping(value="/sendMessage",method = RequestMethod.POST)
    public ResponseEntity<TimetableMessage> sendTimetableMessage(@RequestBody TimetableMessage message) {
        try {
            timetableService.sendTimetableMessage(message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ServiceMessageException e) {
            return new ResponseEntity<>(message, HttpStatus.NOT_IMPLEMENTED);
        }

    }
}
