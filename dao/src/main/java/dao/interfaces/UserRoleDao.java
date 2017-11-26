package dao.interfaces;

import model.UserRole;

/**
 * User role dao.
 */
public interface UserRoleDao extends GenericDao<UserRole> {

    /**
     * Gets user role entity by role title.
     *
     * @param role String
     * @return UserRole
     */
    UserRole getUserRoleByRole(String role);
}
