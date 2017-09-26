package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime departureTime;

    @Column(name = "ARRIVAL_TIME")
    private LocalDateTime arrivalTime;

    @ManyToOne(targetEntity = Train.class)
    private Train train;

    @ManyToOne(targetEntity = RailWayStation.class)
    private RailWayStation station;

    public Schedule(){
    }

    public Schedule(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
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
