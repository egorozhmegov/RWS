package dao.interfaces;

import model.Schedule;
import model.Train;
import java.util.List;

/*
Train dao interface. Extends generic interface.
 */
public interface TrainDao extends GenericDao<Train>{
    /**
     * Get train route
     *
     * @param trainId long
     * @return List<Schedule>
     */
    List<Schedule> getRoute(long trainId);

    /**
     * Get train departure time.
     *
     * @param trainId long
     * @param stationId long
     * @param weekDay weekDay
     * @return String
     */
    String getDepartureTime(long trainId, long stationId, int weekDay);

    /**
     * Get train by number.
     *
     * @param trainNumber String
     * @return Train
     */
    Train getTrainByNumber(String number);
}
