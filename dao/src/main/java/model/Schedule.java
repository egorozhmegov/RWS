package model;

import javax.persistence.*;
import java.io.Serializable;

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
    private String departureTime;

    @Column(name = "ARRIVAL_TIME")
    private String arrivalTime;

    @Column(name = "DEPARTURE_DAY")
    private int departureDay;

    @Column(name = "ARRIVAL_DAY")
    private int arrivalDay;

    @Column(name = "DEPART_PERIOD")
    private String departPeriod;

    @Column(name = "ARRIVE_PERIOD")
    private String arrivePeriod;

    @ManyToOne
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private RailWayStation station;

    public Schedule(){
    }

    public Schedule(String departureTime,
                    String arrivalTime,
                    int departureDay,
                    int arrivalDay,
                    String departPeriod,
                    String arrivePeriod) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureDay = departureDay;
        this.arrivalDay = arrivalDay;
        this.departPeriod = departPeriod;
        this.arrivePeriod = arrivePeriod;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
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

    public String getDepartPeriod() {
        return departPeriod;
    }

    public void setDepartPeriod(String departPeriod) {
        this.departPeriod = departPeriod;
    }

    public String getArrivePeriod() {
        return arrivePeriod;
    }

    public void setArrivePeriod(String arrivePeriod) {
        this.arrivePeriod = arrivePeriod;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureDay=" + departureDay +
                ", arrivalDay=" + arrivalDay +
                ", departPeriod='" + departPeriod + '\'' +
                ", arrivePeriod='" + arrivePeriod + '\'' +
                '}';
    }
}

