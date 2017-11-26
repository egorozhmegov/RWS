package dao.implementation;

import dao.interfaces.UserRoleDao;
import model.UserRole;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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


    /**
     * Gets user role entity by role title.
     *
     * @param role String
     * @return UserRole
     */
    @Transactional
    @Override
    public UserRole getUserRoleByRole(String role) {
        try{
            String sqlQuery = String.format("SELECT u FROM UserRole AS u WHERE u.role = '%s'", role);
            Query query = getEntityManager().createQuery(sqlQuery);
            return (UserRole) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
