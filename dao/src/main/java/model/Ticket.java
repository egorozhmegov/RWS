package model;

import javax.persistence.*;
import java.io.Serializable;

/*
Class of ticket entity. Use for print information about passenger travel and price of travel.
 */

@Entity
@Table(name = "TICKET")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ticketId;

    @Column(name = "PRICE")
    private int ticketPrice;

    @OneToOne(mappedBy = "ticket")
    private Passenger passenger;

    public Ticket(){
    }

    public Ticket(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketPrice=" + ticketPrice +
                ", passenger=" + passenger +
                '}';
    }
}