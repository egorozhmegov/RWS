package service.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;

public interface TrainService extends GenericService<Train> {
    /**
     * Get root points by id.
     *
     * @param id train id.
     * @return List<RootPoint>.
     */
    List<RailWayStation> getRootPointsById(int id);
}
