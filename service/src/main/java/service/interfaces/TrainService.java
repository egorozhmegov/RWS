package service.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;
import java.util.Set;

/**
 * Train service.
 */
public interface TrainService extends GenericService<Train> {
    /**
     * Get route point by id.
     *
     * @param id train id.
     * @return List<RootPoint>.
     */
    Set<RailWayStation> getRoutePointById(long id);

    boolean existTrain(String number);
}
