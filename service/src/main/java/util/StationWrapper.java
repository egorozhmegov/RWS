package util;

import java.time.LocalDate;

public class StationWrapper {

    private String station;

    private LocalDate date;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StationWrapper{" +
                "station='" + station + '\'' +
                ", date=" + date +
                '}';
    }
}
