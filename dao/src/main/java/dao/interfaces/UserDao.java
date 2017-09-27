package dao.interfaces;

import model.Employee;
import model.User;

/*
User dao interface. Extends generic interface.
 */
public interface UserDao extends GenericDao<User> {
    /**
     * Get user account by login.
     *
     * @param login user login.
     * @return User.
     */
    User getUserByLoginAndPassword(String login, String password);

    /**
     * Gets user by login.
     *
     * @param login user login.
     * @return User.
     */
    User getUserByLogin(String login);

    /**
     * Get employee by Fist Name and Last Name.
     *
     * @param firstName String
     * @param lastName String
     * @return Employee
     */
    Employee getEmployeeByFirstNameAndLastName(String firstName, String lastName);
}
