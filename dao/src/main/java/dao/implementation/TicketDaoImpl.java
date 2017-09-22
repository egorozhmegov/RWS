package dao.implementation;

import dao.interfaces.TicketDao;
import model.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 Ticket dao implementation.
 */
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {
    /**
     * Injected instance of entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    public TicketDaoImpl(Class<Ticket> genericClass) {
        super(genericClass);
    }
}
