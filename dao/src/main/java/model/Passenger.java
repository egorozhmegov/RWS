package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/*
Class of passenger entity. Use for buy tickets by passenger and registration him on train.
 */
@Entity
@Table(name = "PASSENGER")
public class Passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSENGER_ID")
    private long id;

    @Column(name = "FIRST_NAME")
    private String passengerFirstName;

    @Column(name = "LAST_NAME")
    private String passengerLastName;

    @Column(name = "BIRTH_DAY")
    private LocalDate birthday;

    @Column(name = "TRAIN_DATE")
    private LocalDate trainDate;

    @ManyToOne
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private RailWayStation station;

    @ManyToOne
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;

    public Passenger(){
    }


    public Passenger(String passengerFirstName, String passengerLastName, LocalDate birthday) {
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDate getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(LocalDate trainDate) {
        this.trainDate = trainDate;
    }

    public RailWayStation getStation() {
        return station;
    }

    public void setStation(RailWayStation station) {
        this.station = station;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", birthday=" + birthday +
                ", trainDate=" + trainDate +
                ", train=" + train +
                ", station=" + station +
                ", ticket=" + ticket +
                '}';
    }
}
