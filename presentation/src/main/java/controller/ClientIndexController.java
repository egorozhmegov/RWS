package controller;

import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.interfaces.ClientService;

import java.util.List;

@Controller
public class ClientIndexController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/client/search", method = RequestMethod.POST)
    public ModelAndView searchTrain(@RequestParam String station1,
                                    @RequestParam String station2,
                                    @RequestParam String date
                                    ){
        ModelAndView modelAndView = new ModelAndView();
        List<Schedule> trains = clientService.searchTrains(station1, station2, date);
        modelAndView.addObject("trains", trains);
        modelAndView.addObject("station1", station1);
        modelAndView.addObject("station2", station2);
        modelAndView.addObject("date", date);
        modelAndView.setViewName("trains");
        return modelAndView;
    }

    @RequestMapping(value="/trainInfo/{id}/{station1}/{station2}/{month}/{day}/{year}",method = RequestMethod.GET)
    public ModelAndView trainInfo(@PathVariable long id,
                                  @PathVariable String station1,
                                  @PathVariable String station2,
                                  @PathVariable String month,
                                  @PathVariable String day,
                                  @PathVariable String year){
        List<Schedule> currentRoute =
                clientService.getCurrentRoute(id, station1, station2);
        int ticketPrice = clientService.getTicketPrice(currentRoute);
        int freeSeats = clientService.getFreeSeats(month, day, year);
        return null;
    }
}
