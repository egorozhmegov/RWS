package service.interfaces;

import model.UserSession;

/**
 * User session service.
 */
public interface UserSessionService extends GenericService<UserSession> {
    /**
     * Create user session.
     *
     * @param sessionId String
     */
    void createUserSession(String sessionId);

    /**
     * Gets user session.
     *
     * @param sessionId String.
     * @return UserSession.
     */
    UserSession getUserSession(String sessionId);

    /**
     * Update user session.
     *
     * @param userSession UserSession.
     */
    void updateUserSession(UserSession userSession);

    /**
     * Removes session.
     *
     * @param sessionId String
     */
    void removeSession(String sessionId);

    /**
     * Remove all expired session.
     */
    void removeExpiredSessionIds();
}
