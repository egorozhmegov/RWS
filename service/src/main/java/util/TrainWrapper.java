package util;

import model.Schedule;
import model.Train;

import java.time.LocalTime;
import java.util.List;

/**
 * Class schedule wrapper. Use for view data on client api.
 */
public class TrainWrapper {

    private Train train;

    private LocalTime departTime;

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

    @Override
    public String toString() {
        return "TrainWrapper{" +
                "train=" + train +
                ", departTime=" + departTime +
                ", price=" + price +
                ", seats=" + seats +
                ", route=" + route +
                '}';
    }
}
