package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.UserSessionDao;
import model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserSessionService;

import java.time.LocalDateTime;

@Service("userSessionServiceImpl")
public class UserSessionServiceImpl extends GenericServiceImpl<UserSession> implements UserSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSessionServiceImpl.class);

    public static final int SESSION_TIME = 5;

    @Autowired
    private UserSessionDao userSessionDao;

    /**
     * Create user session.
     *
     * @param sessionId String
     */
    @Transactional
    @Override
    public void createUserSession(String sessionId) {
        LOG.info("Created session with id: {}", sessionId);
        userSessionDao.create(new UserSession(sessionId, LocalDateTime.now().plusMinutes(SESSION_TIME)));
    }

    /**
     * Gets user session.
     *
     * @param sessionId String.
     * @return UserSession.
     */
    @Transactional
    @Override
    public UserSession getUserSession(String sessionId){
        LOG.info("Gets user session: {}", sessionId);
        return userSessionDao.getUserSessionById(sessionId);
    }

    /**
     * Update user session.
     *
     * @param userSession UserSession.
     */
    @Transactional
    @Override
    public void updateUserSession(UserSession userSession) {
        LOG.info("Update user session: {}", userSession);
        userSessionDao.update(userSession);
    }

    /**
     * Removes session.
     *
     * @param sessionId String
     */
    @Transactional
    @Override
    public void removeSession(String sessionId){
        LOG.info("Remove user session: {}", sessionId);
        userSessionDao.removeSession(sessionId);
    }

    /**
     * Remove all expired session.
     */
    @Transactional
    @Override
    public void removeExpiredSessionIds() {
        for(UserSession session: userSessionDao.getAll()){
            if(session.getExpiredTime().isBefore(LocalDateTime.now())){
                userSessionDao.delete(session.getId());
                LOG.info("Remove expired session: {}", session);
            }
        }
    }

    @Override
    GenericDao<UserSession> getDao() {
        return userSessionDao;
    }

    public void setUserSessionDao(UserSessionDao userSessionDao) {
        this.userSessionDao = userSessionDao;
    }
}
