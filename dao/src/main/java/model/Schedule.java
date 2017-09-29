package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

/*
Class of schedule entity. Use for information about train arrival and departure time.
 */

@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    private long id;

    @Column(name = "DEPARTURE_TIME")
    private LocalTime departureTime;

    @Column(name = "ARRIVAL_TIME")
    private LocalTime arrivalTime;

    @Column(name = "DEPARTURE_DAY")
    private int departureDay;

    @Column(name = "ARRIVAL_DAY")
    private int arrivalDay;

    @Column(name = "SEATS")
    private int trainSeats;

    @ManyToOne
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private RailWayStation station;

    public Schedule(){
    }

    public Schedule(LocalTime departureTime,
                    LocalTime arrivalTime,
                    int departureDay,
                    int arrivalDay,
                    int trainSeats) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureDay = departureDay;
        this.arrivalDay = arrivalDay;
        this.trainSeats = trainSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(int departureDay) {
        this.departureDay = departureDay;
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public int getTrainSeats() {
        return trainSeats;
    }

    public void setTrainSeats(int trainSeats) {
        this.trainSeats = trainSeats;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public RailWayStation getStation() {
        return station;
    }

    public void setStation(RailWayStation station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", departureDay=" + departureDay +
                ", arrivalDay=" + arrivalDay +
                ", trainSeats=" + trainSeats +
                ", train=" + train +
                ", station=" + station +
                '}';
    }
}
