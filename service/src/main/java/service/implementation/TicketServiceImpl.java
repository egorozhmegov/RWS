package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.TicketDao;
import model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.TicketService;


/**
 * Ticket service implementation.
 */
@Service("ticketServiceImpl")
public class TicketServiceImpl extends GenericServiceImpl<Ticket> implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    GenericDao<Ticket> getDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
