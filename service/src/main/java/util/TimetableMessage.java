package util;

/**
 * Class timetable message. Use for pass information on timetable.
 */
public class TimetableMessage {

    private String station;

    private String train;

    private String message;

    public TimetableMessage() {
    }

    public TimetableMessage(String station, String train, String message) {
        this.station = station;
        this.train = train;
        this.message = message;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TimetableMessage{" +
                "station='" + station + '\'' +
                ", train='" + train + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
