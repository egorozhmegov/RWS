package dao.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;

/*
Train dao interface. Extends generic interface.
 */

public interface TrainDao extends GenericDao<Train>{
    /**
     * Get route point by id.
     *
     * @param id train id.
     * @return RailWayStation.
     */
    List<RailWayStation> getRoutePointById(int id);
}
