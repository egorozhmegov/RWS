package util;

import model.Passenger;

/**
 * Ticket data. Use for forward data from frontend to backend after payment.
 */
public class TicketData {

    private Passenger passenger;

    private CreditCard creditCard;

    private TrainWrapper trainWrapper;

    private String userEmail;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public TrainWrapper getTrainWrapper() {
        return trainWrapper;
    }

    public void setTrainWrapper(TrainWrapper trainWrapper) {
        this.trainWrapper = trainWrapper;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
