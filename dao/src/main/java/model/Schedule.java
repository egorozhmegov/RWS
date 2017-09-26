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
    private long scheduleId;

    @Column(name = "DEPARTURE_TIME")
    private LocalTime departureTime;

    @Column(name = "ARRIVAL_TIME")
    private LocalTime arrivalTime;

    @Column(name = "ARRIVAL_DAY")
    private String arrivalDay;

    @Column(name = "DEPARTURE_DAY")
    private String departureDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STATION_ID")
    private RailWayStation station;

    public Schedule(){
    }

    public Schedule(LocalTime departureTime, LocalTime arrivalTime, String arrivalDay, String departureDay) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.arrivalDay = arrivalDay;
        this.departureDay = departureDay;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
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

    public String getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(String arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public String getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(String departureDay) {
        this.departureDay = departureDay;
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
}
