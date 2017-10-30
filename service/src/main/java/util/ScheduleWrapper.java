package util;

import model.Schedule;

import java.util.List;

/**
 * Class schedule wrapper. Use for view data on client api.
 */
public class ScheduleWrapper {

    private List<Schedule> arrivalSchedule;

    private List<Schedule> departureSchedule;

    public List<Schedule> getArrivalSchedule() {
        return arrivalSchedule;
    }

    public void setArrivalSchedule(List<Schedule> arrivalSchedule) {
        this.arrivalSchedule = arrivalSchedule;
    }

    public List<Schedule> getDepartureSchedule() {
        return departureSchedule;
    }

    public void setDepartureSchedule(List<Schedule> departureSchedule) {
        this.departureSchedule = departureSchedule;
    }
}
