package service.interfaces;


import model.UserRole;

public interface UserRoleService extends GenericService<UserRole> {
    /**
     * Gets user role entity by role title.
     *
     * @param role String
     * @return UserRole
     */
    UserRole getUserRoleByRole(String role);
}
