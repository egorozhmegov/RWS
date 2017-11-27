package dao.interfaces;

import model.UserSession;

/**
 * User session dao.
 */
public interface UserSessionDao extends GenericDao<UserSession> {

    /**
     * Gets user session by id.
     *
     * @param sessionId String
     * @return UserSession
     */
    UserSession getUserSessionById(String sessionId);

    /**
     * Removes session.
     *
     * @param sessionId String
     */
    void removeSession(String sessionId);
}
