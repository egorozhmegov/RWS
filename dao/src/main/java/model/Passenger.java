package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTH_DAY")
    private LocalDate birthday;

    @Column(name = "TRAIN_DATE")
    private LocalDate trainDate;

    @ManyToOne
    @JoinColumn(name = "TRAIN_ID")
    private Train train;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STATION_ID")
    private RailWayStation station;

    private LocalTime time;

    public Passenger(){
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Passenger(String firstName, String lastName, LocalDate birthday, LocalTime time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
