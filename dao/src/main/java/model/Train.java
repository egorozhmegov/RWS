package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
Class of train entity.
 */

@Entity
@Table(name = "TRAIN")
public class Train implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAIN_ID")
    private long id;

    @Column(name = "TRAIN_NUMBER")
    private String number;

    @Column(name = "SEATS")
    private int seats;

    @Column(name = "TARIFF")
    private int tariff;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TRAIN_STATION",
            joinColumns = @JoinColumn(name = "TRAIN_ID"),
            inverseJoinColumns = @JoinColumn(name = "STATION_ID"))
    @JsonIgnore
    private Set<RailWayStation> route = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TRAIN_PASSENGER",
            joinColumns = @JoinColumn(name = "TRAIN_ID"),
            inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID"))
    @JsonIgnore
    private Set<Passenger> registeredPassengers = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TRAIN_PERIOD",
            joinColumns = @JoinColumn(name = "TRAIN_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERIOD_ID"))
    @JsonIgnore
    private Set<TrainPeriod> period = new HashSet<>();

    public Train(){
    }

    public Train(String trainNumber, int tariff) {
        this.number = trainNumber;
        this.tariff = tariff;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(int tariff) {
        this.tariff = tariff;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Set<RailWayStation> getRoute() {
        return route;
    }

    public void setRoute(Set<RailWayStation> route) {
        this.route = route;
    }

    public Set<Passenger> getRegisteredPassengers() {
        return registeredPassengers;
    }

    public void setRegisteredPassengers(Set<Passenger> registeredPassengers) {
        this.registeredPassengers = registeredPassengers;
    }

    public Set<TrainPeriod> getPeriod() {
        return period;
    }

    public void setPeriod(Set<TrainPeriod> period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", priceBetweenNearestStation=" + tariff +
                '}';
    }
}
