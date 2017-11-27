package service.implementation;

import dao.interfaces.UserSessionDao;
import model.UserSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSessionServiceImplTest {

    @InjectMocks
    private UserSessionServiceImpl userSessionService;

    @Mock
    private UserSessionDao userSessionDao;

    @Test
    public void createUserSessionPos0(){
        userSessionService.createUserSession(null);
    }

    @Test
    public void getUserSessionPos0(){
        UserSession userSession = new UserSession();
        when(userSessionDao.getUserSessionById("")).thenReturn(userSession);
        assertEquals(userSession, userSessionService.getUserSession(""));
    }

    @Test
    public void updateUserSessionPos0(){
        UserSession userSession = new UserSession();
        userSessionService.updateUserSession(userSession);
        verify(userSessionDao).update(userSession);
    }

    @Test
    public void removeSessionPos0(){
        UserSession userSession = new UserSession();
        userSessionService.removeSession("");
        verify(userSessionDao).removeSession("");
    }

    @Test
    public void removeExpiredSessionIdsPos0(){
        UserSession userSession = new UserSession();
        userSession.setExpiredTime(LocalDateTime.now().minusHours(10));
        userSession.setId(1L);
        List<UserSession> sessions = new ArrayList<>();
        sessions.add(userSession);
        when(userSessionDao.getAll()).thenReturn(sessions);
        userSessionService.removeExpiredSessionIds();
        verify(userSessionDao).delete(userSession.getId());
    }

}