package service.interfaces;

import model.RailWayStation;
import model.Train;
import java.util.List;
import java.util.Set;

/**
 * Train service.
 */
public interface TrainService extends GenericService<Train> {
    boolean existTrain(String number);
}
