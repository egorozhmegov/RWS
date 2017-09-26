package service.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;

public interface TrainService extends GenericService<Train> {
    /**
     * Get route point by id.
     *
     * @param id train id.
     * @return List<RootPoint>.
     */
    List<RailWayStation> getRoutePointById(int id);

    boolean existTrain(String number);
}
