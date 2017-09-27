package controller;

import model.RailWayStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.interfaces.RailWayStationService;

import java.util.List;

@RestController
public class RailWayStationController {

    private final static Logger LOG = LoggerFactory.getLogger(RailWayStationController.class);

    @Autowired
    private RailWayStationService railWayStationService;

    @RequestMapping(value="/view/list_stations",method = RequestMethod.GET)
    public List<RailWayStation> getAllStations() {
        LOG.info("Stations list loaded.");
        return railWayStationService.getAll();
    }

    @RequestMapping(value="/view/add_station",method = RequestMethod.POST)
    public boolean addStation(@RequestBody RailWayStation station) {
        if(railWayStationService.existStation(station.getTitle())){
            LOG.info(String.format("Station with name: %s exist already.", station.getTitle()));
            return false;
        } else {
            LOG.info(String.format("Create station with name: %s", station.getTitle()));
            railWayStationService.create(station);
            return true;
        }
    }

    @RequestMapping(value="/view/remove_station/{stationId}",method = RequestMethod.DELETE)
    public void removeStation(@PathVariable("stationId") int stationId) {
        LOG.info(String.format("Remove station: %s", railWayStationService.read(stationId).getTitle()));
        railWayStationService.delete(stationId);
    }

    @RequestMapping(value="/view/update_station",method = RequestMethod.PUT)
    public void updateStation(@RequestBody RailWayStation station) {
        LOG.info(String.format("Station %s updated.", station.getTitle()));
        railWayStationService.update(station);
    }
}
