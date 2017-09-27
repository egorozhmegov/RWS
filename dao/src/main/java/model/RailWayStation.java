package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
Class of railway station entity. Use for information about train schedule.
 */

@Entity
@Table(name = "STATION")
public class RailWayStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID")
    private long stationId;

    @Column(name = "TITLE")
    private String stationName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="STATION_SCHEDULE",
            joinColumns = @JoinColumn(name = "STATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "SCHEDULE_ID"))
    private Set<Schedule> schedule = new HashSet<>();


    @ManyToMany(mappedBy = "route")
    private Set<Train> trains = new HashSet<>();

    public RailWayStation(){
    }

    public RailWayStation(String stationName) {
        this.stationName = stationName;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Set<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Set<Train> getTrains() {
        return trains;
    }

    public void setTrains(Set<Train> trains) {
        this.trains = trains;
    }

    @Override
    public String toString() {
        return "RailWayStation{" +
                "stationId=" + stationId +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
