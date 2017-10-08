package dao.implementation;

import dao.interfaces.UserDao;
import model.Employee;
import model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 User dao implementation.
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

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
    public User getUserByLoginAndPassword(String login, String password) {
        try{
            String sqlQuery = String
                    .format("SELECT u FROM User AS u WHERE u.login = '%s' AND u.password = '%s'", login, password);
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
            String sqlQuery = String.format("SELECT u FROM User AS u WHERE u.login = '%s'", login);
            Query query = getEntityManager().createQuery(sqlQuery);
            return (User) query.getSingleResult();
        } catch (Throwable e){
            return null;
        }
    }

    /**
     * Get employee by Fist Name and Last Name.
     *
     * @param firstName String
     * @param lastName String
     * @return Employee
     */
    @Override
    public Employee getEmployeeByFirstNameAndLastName(String firstName, String lastName) {
        try{
            String query = String.format("SELECT e FROM Employee AS e WHERE e.firstName = '%s' AND e.lastName = '%s'", firstName, lastName);
            return (Employee) getEntityManager().createQuery(query).getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
}
