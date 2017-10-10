package controller;

import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

        System.out.println(trains);

        modelAndView.addObject("trains", trains);
        modelAndView.setViewName("trains");
        return modelAndView;
    }
}
