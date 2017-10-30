package util;

import model.RailWayStation;

import java.time.LocalDate;

/**
 * Class schedule wrapper. Use for view data on client api.
 */
public class SearchTrain {

    private RailWayStation stationFrom;

    private RailWayStation stationTo;

    private LocalDate departDate;

    public RailWayStation getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(RailWayStation stationFrom) {
        this.stationFrom = stationFrom;
    }

    public RailWayStation getStationTo() {
        return stationTo;
    }

    public void setStationTo(RailWayStation stationTo) {
        this.stationTo = stationTo;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public void setDepartDate(LocalDate departDate) {
        this.departDate = departDate;
    }
}
