package dao.implementation;

import dao.interfaces.UserSessionDao;
import model.UserSession;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserSessionDaoImpl extends GenericDaoImpl<UserSession> implements UserSessionDao {

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    UserSessionDaoImpl(Class<UserSession> genericClass) {
        super(genericClass);
    }

    public UserSessionDaoImpl() {
        super(UserSession.class);
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Gets user session by id.
     *
     * @param sessionId String
     * @return UserSession
     */
    @Override
    public UserSession getUserSessionById(String sessionId) {
        try{
            String sqlQuery = String.format("SELECT u FROM UserSession AS u WHERE u.sessionId = '%s'", sessionId);
            Query query = getEntityManager().createQuery(sqlQuery);
            return (UserSession) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
