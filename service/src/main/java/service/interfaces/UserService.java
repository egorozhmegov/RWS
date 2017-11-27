package service.interfaces;

import model.Employee;
import model.User;

/**
 * User service.
 */
public interface UserService extends GenericService<User> {
    /**
     * Register new user.
     *
     * @param user user.
     */
    void registerUser(User user);

    /**
     * Gets user by login.
     *
     * @param login login.
     * @return User.
     */
    User getUserByLogin(String login);

    /**
     * Gets user by email.
     *
     * @param email String.
     * @return User.
     */
    User getUserByEmail(String email);

    /**
     * Check login and password of user.
     *
     * @param authentication User
     * @return User
     */
    User authenticate(User authentication);

    /**
     * Get employee by Fist Name and Last Name.
     *
     * @param firstName String
     * @param lastName String
     * @return Employee
     */
    Employee getEmployeeByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Create user session.
     *
     * @param sessionId String
     */
    void createUserSession(String sessionId);
}
