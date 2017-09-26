package dao.implementation;

import dao.interfaces.UserRoleDao;
import model.UserRole;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User role dao implementation
 */
public class UserRoleDaoImpl extends GenericDaoImpl<UserRole> implements UserRoleDao {
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleDaoImpl() {
        super(UserRole.class);
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public UserRoleDaoImpl(Class<UserRole> genericClass) {
        super(genericClass);
    }
}
