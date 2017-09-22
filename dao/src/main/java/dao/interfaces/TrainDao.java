package dao.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;

/*
Train dao interface. Extends generic interface.
 */

public interface TrainDao extends GenericDao<Train>{
    /**
     * Gets root points by id.
     *
     * @param id train id.
     * @return RailWayStation.
     */
    List<RailWayStation> getRootPointsById(int id);
}
