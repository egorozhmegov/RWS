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
    @Column(name = "ID")
    private long trainId;

    @Column(name = "TRAIN_NUMBER")
    private String trainNumber;

    @Column(name = "SEATS")
    private short seats;

    @Column(name = "TARIFF")
    private int tariff;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RailWayStation> rootPoints = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Passenger> registeredPassengers = new ArrayList<>();

    public Train(){
    }

    public Train(String trainNumber, int tariff) {
        this.trainNumber = trainNumber;
        this.tariff = tariff;
        seats = 92;
    }

    public List<RailWayStation> getRootPoints() {
        return rootPoints;
    }

    public void setRootPoints(List<RailWayStation> rootPoints) {
        this.rootPoints = rootPoints;
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

    public short getSeats() {
        return seats;
    }

    public void setSeats(short seats) {
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
