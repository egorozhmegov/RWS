package service.implementation;

import exception.ClientServiceException;
import model.RailWayStation;
import model.Schedule;
import model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.*;
import java.time.LocalDate;
import java.util.*;

/**
 *Client service implementation.
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private final static Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RailWayStationService stationService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PassengerService passengerService;

    /**
     * Get list of schedule trains by two station and date.
     *
     * @param station1 String
     * @param station2 String
     * @param date String
     * @return Map<Schedule,Integer>
     */
    @Override
    public Map<Schedule, Integer> searchTrains(String station1,
                                               String station2,
                                               String date){

        Map<Schedule, Integer> searchResult = new HashMap<>();

        if(station1 == null
                || station1.trim().isEmpty()
                || station2 == null
                || station2.trim().isEmpty()
                || date == null
                || date.trim().isEmpty()){
            LOG.info("Not valid search data.");
            throw new ClientServiceException("Not valid search data.");
        }
        RailWayStation departStation = stationService.getStationByTitle(station1);
        RailWayStation arriveStation = stationService.getStationByTitle(station2);
        int day = dayOfWeek(parseDate(date));

        List<Schedule> trains = scheduleService
                .searchTrain(departStation.getId(), arriveStation.getId(), day);

        for(Schedule schedule: trains){
            Train train = schedule.getTrain();
            searchResult.put(schedule,
                    getTicketPrice(train.getId(),
                            getCurrentRoute(train.getId(),
                                    station1, station2)));
        }

        return searchResult;
    }


    /**
     *Get number of week day.
     *
     * @param date LocalDate
     * @return int
     */
    public int dayOfWeek(LocalDate date){
        return TrainServiceImpl
                .WEEK_DAYS
                .indexOf(date
                        .getDayOfWeek()
                        .toString()
                        .toLowerCase()
                        .substring(0,3)) + 1;
    }

    /**
     * Parse string date to object LocalDate.
     *
     * @param date String
     * @return LocalDate
     */
    public LocalDate parseDate(String date){
        return LocalDate.of(
                Integer.parseInt(date.split("/")[2]),
                Integer.parseInt(date.split("/")[0]),
                Integer.parseInt(date.split("/")[1]));
    }

    /**
     * Get train route for client request.
     *
     * @param trainId long
     * @param station1 String
     * @param station2 String
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getCurrentRoute(long trainId, String station1, String station2) {
        List<Schedule> trainRoute = trainService.getRoute(trainId);
        List<Schedule> currentRoute = new ArrayList<>();

        boolean isCurrentRoute = false;
        for (Schedule routePoint: trainRoute){
            if(Objects.equals(routePoint.getStation().getTitle(), station1)){
                isCurrentRoute = true;
            } else if(Objects.equals(routePoint.getStation().getTitle(), station2)){
                currentRoute.add(routePoint);
                isCurrentRoute = false;
            }
            if(isCurrentRoute) currentRoute.add(routePoint);
        }
        return currentRoute;
    }

    /**
     * Get ticket price.
     *
     * @param currentRoute List<Schedule>.
     * @param trainId long.
     * @return int.
     */
    @Override
    public int getTicketPrice(long trainId, List<Schedule> currentRoute) {
        return trainService
                .read(trainId)
                .getTariff()*currentRoute.size();
    }

    /**
     * Get count of free seats in train.
     *
     * @param month String
     * @param day String
     * @param year String
     * @param id long
     * @param station1 String
     * @param station2 String
     * @return int
     */
    @Override
    public int getFreeSeats(String month,
                     String day,
                     String year,
                     long id,
                     String station1,
                     String station2) {
        long departStationId = stationService.getStationByTitle(station1).getId();
        long arriveStationId = stationService.getStationByTitle(station2).getId();
        LocalDate departDay = LocalDate
                    .of(Integer.parseInt(year),
                        Integer.parseInt(month),
                        Integer.parseInt(day));


        return Train.SEATS - passengerService
                .getRegisteredPassengers(
                    id,
                    departStationId,
                    arriveStationId,
                    departDay)
                .size();
    }
}
