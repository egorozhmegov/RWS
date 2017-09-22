package dao.interfaces;

import model.RailWayStation;

/*
Railway station dao interface. Extends generic interface.
 */

public interface RailWayStationDao extends GenericDao<RailWayStation>{
    /**
     * Gets RailWayStation by name.
     *
     * @param name station name.
     * @return RailWayStation.
     */
    RailWayStation getStationByName(String name);
}
