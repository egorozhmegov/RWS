package service.implementation;

import dao.interfaces.UserDao;
import exception.UserServiceInvalidDataException;
import model.Employee;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Test
    public void getUserByLoginPos0(){
        User user = new User();
        when(userDao.getUserByLogin("")).thenReturn(user);
        assertEquals(user, userService.getUserByLogin(""));
    }

    @Test
    public void getUserByEmailPos0(){
        User user = new User();
        when(userDao.getUserByEmail("")).thenReturn(user);
        assertEquals(user, userService.getUserByEmail(""));
    }

    @Test
    public void getEmployeeByFirstNameAndLastNamePos0(){
        Employee employee = new Employee();
        when(userDao.getEmployeeByFirstNameAndLastName("", "")).thenReturn(employee);
        assertEquals(employee, userService.getEmployeeByFirstNameAndLastName("", ""));
    }

    @Test(expected = UserServiceInvalidDataException.class)
    public void authenticatePos0(){
        User user = new User();
        user.setLogin("root");
        user.setPassword("root");
        when(userDao.getUserByLoginAndPassword("root", "root")).thenReturn(user);
        assertEquals(user, userService.authenticate(user));
    }

    @Test(expected = NullPointerException.class)
    public void registerUserNeg0(){
        User user = new User();
        user.setUserFirstName("root");
        user.setUserLastName("root");
        user.setEmail("root");
        user.setLogin("root");
        user.setPassword("root");
        Employee employee = new Employee();

        when(userDao.getUserByLogin("root")).thenReturn(null);
        when(userDao.getUserByEmail("root")).thenReturn(null);
        when(userDao.getEmployeeByFirstNameAndLastName("root","root")).thenReturn(employee);
        userService.registerUser(user);
        verify(userDao).create(user);
    }
}