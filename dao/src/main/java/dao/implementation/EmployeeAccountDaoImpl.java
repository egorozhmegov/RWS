package dao.implementation;

import dao.interfaces.EmployeeAccountDao;
import model.EmployeeAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 Employee account dao implementation.
 */
public class EmployeeAccountDaoImpl extends GenericDaoImpl<EmployeeAccount> implements EmployeeAccountDao {
    private final static Logger LOG = LoggerFactory.getLogger(EmployeeAccountDaoImpl.class);

    public EmployeeAccountDaoImpl() {
        super(EmployeeAccount.class);
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

    public EmployeeAccountDaoImpl(Class<EmployeeAccount> genericClass) {
        super(genericClass);
    }

    /**
     * Gets employee account by login.
     *
     * @param login employee login.
     * @return EmployeeAccountController.
     */
    @Override
    public EmployeeAccount getByLoginAndPassword(String login, String password) {
        try{
            String sqlQuery = String
                    .format("SELECT e FROM EmployeeAccount AS e WHERE e.employeeLogin = '%s' AND e.employeePassword = '%s'",login, password);
            Query query = getEntityManager().createQuery(sqlQuery);
            EmployeeAccount employeeAccount = (EmployeeAccount) query.getSingleResult();
            LOG.info(String.format("Found employee with login: %s.", login));
            return employeeAccount;
        } catch (Throwable e){
            LOG.info(String.format("No found employee with login: %s.", login));
            return null;
        }
    }

    /**
     * Gets employee account by login.
     *
     * @param login employee login.
     * @return EmployeeAccountController.
     */
    @Override
    public EmployeeAccount getByLogin(String login) {
        try{
            String sqlQuery = String.format("SELECT e FROM EmployeeAccount AS e WHERE e.employeeLogin = '%s'",login);
            Query query = getEntityManager().createQuery(sqlQuery);
            EmployeeAccount employeeAccount = (EmployeeAccount) query.getSingleResult();
            LOG.info(String.format("Found employee with login: %s.", login));
            return employeeAccount;
        } catch (Throwable e){
            LOG.info(String.format("No found employee with login: %s.", login));
            return null;
        }
    }
}
