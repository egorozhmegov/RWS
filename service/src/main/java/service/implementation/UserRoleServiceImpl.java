package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.UserRoleDao;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserRoleService;

/**
 * User role service implementation.
 */
@Service("userRoleServiceImpl")
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    GenericDao<UserRole> getDao() {
        return userRoleDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * Gets user role entity by role title.
     *
     * @param role String
     * @return UserRole
     */
    @Transactional
    @Override
    public UserRole getUserRoleByRole(String role) {
        return userRoleDao.getUserRoleByRole(role);
    }
}
