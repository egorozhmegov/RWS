package service.interfaces;

import exception.UserServiceException;
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
     * Check login and password of user.
     *
     * @param authentication
     * @return User
     */
    User authenticate(User authentication) throws UserServiceException;
}
