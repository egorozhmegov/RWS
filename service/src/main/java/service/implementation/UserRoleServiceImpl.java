package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.UserRoleDao;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import service.interfaces.UserRoleService;

/**
 * User role service implementation.
 */
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    GenericDao<UserRole> getDao() {
        return userRoleDao;
    }

    public UserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }
}
