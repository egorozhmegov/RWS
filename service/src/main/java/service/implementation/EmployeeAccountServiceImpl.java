package service.implementation;

import dao.interfaces.EmployeeAccountDao;
import dao.interfaces.GenericDao;
import model.EmployeeAccount;
import exception.EmployeeAccountExistLoginServiceException;
import exception.EmployeeAccountNullEntityServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.EmployeeAccountService;

/**
 * Employee account service implementation.
 */
@Service("employeeAccountServiceImpl")
public class EmployeeAccountServiceImpl
        extends GenericServiceImpl<EmployeeAccount>
            implements EmployeeAccountService {

    private final static Logger LOG = LoggerFactory.getLogger(EmployeeAccountServiceImpl.class);

    @Autowired
    private EmployeeAccountDao employeeDao;

    /**
     * Get employee by login.
     *
     * @param login login.
     * @return EmployeeAccount.
     */
    @Override
    @Transactional
    public EmployeeAccount getByLogin(String login) {
        return employeeDao.getByLogin(login);
    }

    /**
     * Register new employee.
     *
     * @param employee employee.
     */
    @Override
    @Transactional
    public void registerEmployee(EmployeeAccount employee) {
        if(employee == null
                || employee.getEmployeeFirstName() == null
                || employee.getEmployeeFirstName().isEmpty()
                || employee.getEmployeeLastName() == null
                || employee.getEmployeeLastName().isEmpty()
                || employee.getEmployeeEmail() == null
                || employee.getEmployeeEmail().isEmpty()
                || employee.getEmployeeLogin() == null
                || employee.getEmployeeLogin().isEmpty()
                || employee.getEmployeePassword() == null
                || employee.getEmployeePassword().isEmpty()){
            LOG.info(String.format("Invalid employee data: %s", employee));
            throw new EmployeeAccountNullEntityServiceException("Method: registerEmployee. Null entity.");
        } else if(getByLogin(employee.getEmployeeLogin()) != null){
            LOG.info(String.format("Exist login: %s", employee.getEmployeeLogin()));
            throw new EmployeeAccountExistLoginServiceException("Method: registerEmployee. Exist login.");
        } else {
            employeeDao.create(employee);
            LOG.info(String.format("Employee successfully registered. Employee detailes: %s", employee));
        }
    }

    /**
     * Check login and password of user.
     *
     * @param authentication
     * @return EmployeeAccount
     */
    @Override
    @Transactional
    public EmployeeAccount checkAuthenticate(EmployeeAccount authentication) {

        String login = authentication.getEmployeeLogin();
        String password = authentication.getEmployeePassword();

        final EmployeeAccount employeeAccount = employeeDao.getByLoginAndPassword(login, password);
        if(employeeAccount != null){
            LOG.info(String.format("Success authentication login: %s, password: %s.", login, password));
                return employeeAccount;
        } else {
            LOG.info(String.format("Not valid login: %s, password: %s.", login, password));
            return null;
        }
    }

    public void setEmployeeDao(EmployeeAccountDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    GenericDao<EmployeeAccount> getDao() {
        return employeeDao;
    }
}
