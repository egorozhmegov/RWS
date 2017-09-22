package service.interfaces;

import model.EmployeeAccount;

public interface EmployeeAccountService extends GenericService<EmployeeAccount> {
    /**
     * Register new employee.
     *
     * @param employee employee.
     */
    void registerEmployee(EmployeeAccount employee);

    /**
     * Gets employee by login.
     *
     * @param login login.
     * @return EmployeeAccount.
     */
    EmployeeAccount getByLogin(String login);

    /**
     * Check login and password of user.
     *
     * @param authentication
     * @return EmployeeAccount
     */
    EmployeeAccount checkAuthenticate(EmployeeAccount authentication);
}
