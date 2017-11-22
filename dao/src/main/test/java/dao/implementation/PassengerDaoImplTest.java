package dao.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PassengerDaoImplTest {

    @Mock
    PassengerDaoImpl passengerDao;

    @Test
    public void deleteByTrainId0(){
        long trainId = 1;
        passengerDao.deleteByTrainId(trainId);
        verify(passengerDao).deleteByTrainId(trainId);
    }
}