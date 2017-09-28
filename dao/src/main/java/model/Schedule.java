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
    private String departureDay;

    @Column(name = "ARRIVAL_DAY")
    private String arrivalDay;

    @Column(name = "TRAIN")
    private String train;

    @Column(name = "STATION")
    private String station;

    public Schedule(){
    }

    public Schedule(LocalTime departureTime,
                    LocalTime arrivalTime,
                    String departureDay,
                    String arrivalDay,
                    String train,
                    String station) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureDay = departureDay;
        this.arrivalDay = arrivalDay;
        this.train = train;
        this.station = station;
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

    public String getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(String departureDay) {
        this.departureDay = departureDay;
    }

    public String getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(String arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
