package service.interfaces;

import util.TimetableMessage;

/**
 * Timetable service interface.
 */
public interface TimetableService {
    /**
     * Send message on table
     *
     * @param message TimetableMessage
     */
    void sendTimetableMessage(TimetableMessage message);
}
