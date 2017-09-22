package dao.interfaces;

import model.EmployeeAccount;

/*
Employee account dao interface. Extends generic interface.
 */
public interface EmployeeAccountDao extends GenericDao<EmployeeAccount> {
    /**
     * Gets employee account by login.
     *
     * @param login employee login.
     * @return EmployeeAccountController.
     */
    EmployeeAccount getByLoginAndPassword(String login, String password);

    /**
     * Gets employee account by login.
     *
     * @param login employee login.
     * @return EmployeeAccountController.
     */
    EmployeeAccount getByLogin(String login);
}
