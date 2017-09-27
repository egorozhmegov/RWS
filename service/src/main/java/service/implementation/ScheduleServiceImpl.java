package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.ScheduleDao;
import model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.ScheduleService;

/**
 * Schedule service implementation.
 */
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule> implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    GenericDao<Schedule> getDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }
}
