package util;

/**
 * Class timetable message. Use for pass information on timetable.
 */
public class TimetableMessage {

    private String station;

    private String train;

    private String status;

    private String message;

    public TimetableMessage() {
    }

    public TimetableMessage(String station, String train, String status, String message) {
        this.station = station;
        this.train = train;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimetableMessage{" +
                "station='" + station + '\'' +
                ", train='" + train + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
