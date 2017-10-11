package model;

import javax.persistence.*;
import java.io.Serializable;

/*
Class of train entity.
 */
@Entity
@Table(name = "TRAIN")
public class Train implements Serializable {

    private static final long serialVersionUID = 1L;

    public static int SEATS = 92;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAIN_ID")
    private long id;

    @Column(name = "TRAIN_NUMBER")
    private String number;

    @Column(name = "TARIFF")
    private int tariff;

    public Train(){
    }

    public Train(String number, int tariff) {
        this.number = number;
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

    public int getTariff() {
        return tariff;
    }

    public void setTariff(int tariff) {
        this.tariff = tariff;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", tariff=" + tariff +
                '}';
    }
}
