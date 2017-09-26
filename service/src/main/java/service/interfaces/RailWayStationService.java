package service.interfaces;

import model.RailWayStation;

public interface RailWayStationService extends GenericService<RailWayStation> {
    /**
     * Gets RailWayStation by name.
     *
     * @param name station name.
     * @return RailWayStation.
     */
    RailWayStation getStationByName(String name);

    boolean existStation(String title);
}
