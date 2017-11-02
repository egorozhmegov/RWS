package util;

import model.RailWayStation;
import model.Schedule;
import model.Train;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Class schedule wrapper. Use for view data on client api.
 */
public class TrainWrapper {

    private Train train;

    private RailWayStation stationFrom;

    private RailWayStation stationTo;

    private LocalTime departTime;

    private LocalTime arriveTime;

    private LocalDate departDate;

    private LocalDate arriveDate;

    private int price;

    private int seats;

    private List<Schedule> route;

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalTime departTime) {
        this.departTime = departTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<Schedule> getRoute() {
        return route;
    }

    public void setRoute(List<Schedule> route) {
        this.route = route;
    }

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

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
    }

    public LocalTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public String toString() {
        return "TrainWrapper{" +
                "train=" + train +
                ", stationFrom=" + stationFrom +
                ", stationTo=" + stationTo +
                ", departTime=" + departTime +
                ", arriveTime=" + arriveTime +
                ", departDate=" + departDate +
                ", arriveDate=" + arriveDate +
                ", price=" + price +
                ", seats=" + seats +
                ", route=" + route +
                '}';
    }
}
