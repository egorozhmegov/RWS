package dao.implementation;

import dao.interfaces.UserDao;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 User dao implementation.
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    private final static Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        super(User.class);
    }

    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public UserDaoImpl(final Class<User> genericClass) {
        super(genericClass);
    }

    /**
     * Get user by login.
     *
     * @param login user login.
     * @return User.
     */
    @Override
    public User getUserByLoginAndPassword(final String login, final String password) {
        try{
            String sqlQuery = String
                    .format("SELECT u FROM User AS u WHERE u.userLogin = '%s' AND u.userPassword = '%s'", login, password);
            Query query = getEntityManager().createQuery(sqlQuery);
            return (User) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Get user by login.
     *
     * @param login user login.
     * @return User.
     */
    @Override
    public User getUserByLogin(final String login) {
        try{
            String sqlQuery = String.format("SELECT u FROM User AS u WHERE u.userLogin = '%s'", login);
            Query query = getEntityManager().createQuery(sqlQuery);
            return (User) query.getSingleResult();
        } catch (Throwable e){
            return null;
        }
    }
}
