package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private long trainId;

    @Column(name = "TRAIN_NUMBER")
    private String trainNumber;

    @Column(name = "SEATS")
    private int seats;

    @Column(name = "TARIFF")
    private int tariff;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TRAIN_STATION",
            joinColumns = @JoinColumn(name = "TRAIN_ID"),
            inverseJoinColumns = @JoinColumn(name ="STATION_ID"))
    private List<RailWayStation> route = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="TRAIN_PASSENGER",
            joinColumns = @JoinColumn(name = "TRAIN_ID"),
            inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID"))
    private List<Passenger> registeredPassengers = new ArrayList<>();

    public Train(){
    }

    public Train(String trainNumber, int tariff) {
        this.trainNumber = trainNumber;
        this.tariff = tariff;
        setSeats(92);
    }

    public List<RailWayStation> getRoute() {
        return route;
    }

    public void setRoute(List<RailWayStation> route) {
        this.route = route;
    }

    public List<Passenger> getRegisteredPassengers() {
        return registeredPassengers;
    }

    public void setRegisteredPassengers(List<Passenger> registeredPassengers) {
        this.registeredPassengers = registeredPassengers;
    }

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
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
        if(seats <= 92) this.seats = seats;
        else this.seats = 92;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId=" + trainId +
                ", trainNumber='" + trainNumber + '\'' +
                ", priceBetweenNearestStation=" + tariff +
                '}';
    }
}
