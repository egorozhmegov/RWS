package model;

import javax.persistence.*;
import java.io.Serializable;

/*
Class of employee account entity. Use for user registration and authorization.
 */

@Entity
@Table(name = "EMPLOYEE_ACCOUNT")
public class EmployeeAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long employeeId;

    @Column(name = "FIRST_NAME")
    private String employeeFirstName;

    @Column(name = "LAST_NAME")
    private String employeeLastName;

    @Column(name = "EMAIL")
    private String employeeEmail;

    @Column(name = "LOGIN")
    private String employeeLogin;

    @Column(name = "PASSWORD")
    private String employeePassword;

    public EmployeeAccount(){
    }

    public EmployeeAccount(String employeeFirstName,
                           String employeeLastName,
                           String employeeEmail,
                           String employeeLogin,
                           String employeePassword) {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeEmail = employeeEmail;
        this.employeeLogin = employeeLogin;
        this.employeePassword = employeePassword;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    @Override
    public String toString() {
        return "EmployeeAccountController{" +
                "employeeId=" + employeeId +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeLogin='" + employeeLogin + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                '}';
    }
}
