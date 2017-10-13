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
    @Column(name = "TICKET_ID")
    private long id;

    @Column(name = "PRICE")
    private int ticketPrice;

    public Ticket(){
    }

    public Ticket(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}