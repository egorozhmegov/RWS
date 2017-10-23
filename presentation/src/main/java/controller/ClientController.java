package controller;

import exception.ServiceException;
import model.Passenger;
import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.interfaces.ClientService;
import service.interfaces.RailWayStationService;
import service.interfaces.ScheduleService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


/**
 * Client controller.
 */
@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RailWayStationService railWayStationService;

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stations", railWayStationService.getAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/client/search", method = RequestMethod.POST)
    public ModelAndView searchTrain(@RequestParam String station1,
                                    @RequestParam String station2,
                                    @RequestParam String date
    ) {
        ModelAndView modelAndView = new ModelAndView();

        Map<Schedule, Integer> trains =
                clientService.searchTrains(station1, station2, date);

        modelAndView.addObject("trains", trains);
        modelAndView.addObject("station1", station1);
        modelAndView.addObject("station2", station2);
        modelAndView.addObject("date", date);
        modelAndView.setViewName("trains");

        return modelAndView;
    }

    @RequestMapping(value = "/client/buy/{id}/{station1}/{station2}/{month}/{day}/{year}"
            , method = RequestMethod.GET)
    public ModelAndView trainInfo(@PathVariable long id,
                                  @PathVariable String station1,
                                  @PathVariable String station2,
                                  @PathVariable String month,
                                  @PathVariable String day,
                                  @PathVariable String year) {
        ModelAndView modelAndView = new ModelAndView();

        List<Schedule> currentRoute =
                clientService.getCurrentRoute(id, station1, station2);
        int ticketPrice = clientService.getTicketPrice(id, currentRoute);
        int freeSeats = clientService.getFreeSeats(month, day, year, id, station1, station2);
        int diffDays = currentRoute.get(currentRoute.size() - 1).getArrivalDay()
                - currentRoute.get(0).getDepartureDay();


        LocalDate departDay = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day));
        LocalDate arriveDay = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day) + diffDays);
        LocalTime departTime = currentRoute.get(0).getDepartureTime();
        LocalTime arriveTime = currentRoute.get(currentRoute.size() - 1).getArrivalTime();

        modelAndView.addObject("currentRoute", currentRoute);
        modelAndView.addObject("ticketPrice", ticketPrice);
        modelAndView.addObject("freeSeats", freeSeats);
        modelAndView.addObject("departDay", departDay);
        modelAndView.addObject("arriveDay", arriveDay);
        modelAndView.addObject("departTime", departTime);
        modelAndView.addObject("arriveTime", arriveTime);

        modelAndView.setViewName("buy");

        return modelAndView;
    }

    @RequestMapping(value = "/client/payment", method = RequestMethod.POST)
    public ModelAndView buyTicket(@RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String date,
                                  @RequestParam long trainId,
                                  @RequestParam String departDay,
                                  @RequestParam int freeSeats,
                                  @RequestParam String departStation,
                                  @RequestParam String arriveStation,
                                  @RequestParam int ticketPrice

    ) {
        ModelAndView modelAndView = new ModelAndView();

        try{
            clientService.buyTicket(
                    firstName,
                    lastName,
                    date,
                    trainId,
                    departDay,
                    freeSeats,
                    departStation,
                    arriveStation,
                    ticketPrice);
            Passenger passenger = new Passenger(firstName, lastName, clientService.parseDate(date));
            modelAndView.addObject("passenger", passenger);
            modelAndView.setViewName("successBuy");
        } catch (ServiceException e){
            modelAndView.setViewName("unSuccessBuy");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/client/schedule", method = RequestMethod.POST)
    public ModelAndView getSchedule(@RequestParam String station, @RequestParam String date) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("arriveSchedule",
                scheduleService.getStationArriveSchedule(station, date));
        modelAndView.addObject("departSchedule",
                scheduleService.getStationDepartSchedule(station, date));
        modelAndView.addObject("station", station);
        modelAndView.addObject("date", date);

        modelAndView.setViewName("schedule");
        return modelAndView;
    }
}
