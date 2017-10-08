package service.interfaces;

import model.RailWayStation;

/**
 * Station service.
 */
public interface RailWayStationService extends GenericService<RailWayStation> {
    /**
     * Gets RailWayStation by title.
     *
     * @param title String.
     * @return RailWayStation.
     */
    RailWayStation getStationByTitle(String title);

    /**
     * Add new station.
     *
     * @param station RailWayStation
     */
    void addStation(RailWayStation station);

    /**
     * Delete station.
     *
     * @param id long.
     */
    void removeStation(long id);
}
