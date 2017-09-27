package dao.implementation;

import dao.interfaces.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
Generic dao implementation.
 */
abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private final static Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);

    /**
     * Get entity manager.
     * @return entityManager
     */
    abstract EntityManager getEntityManager();

    /**
     * Class object of parametrized entity.
     */
    private Class<T> genericClass;

    /**
     * Single constructor with generic class as parameter.
     * @param genericClass object Class of entity
     */
    GenericDaoImpl(final Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * Create new entity.
     *
     * @param entity new object.
     */
    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
        LOG.info(String.format("Entity successfully created. Entity details: %s", entity));
    }

    /**
     * Gets entity by id.
     *
     * @param id id.
     * @return T.
     */
    @Override
    public T read(long id) {
        LOG.info("Entity successfully loaded.");
        return getEntityManager().find(genericClass, id);
    }

    /**
     * Update entity.
     *
     * @param entity new object.
     */
    @Override
    public void update(T entity) {
        if (!getEntityManager().contains(entity)) {
            getEntityManager().merge(entity);
        }
        getEntityManager().flush();
        LOG.info(String.format("Entity successfully updated. Entity details: %s", entity));
    }

    /**
     * Delete entity.
     *
     * @param id id.
     */
    @Override
    public void delete(long id) {
        T entity = getEntityManager().find(genericClass, id);
        getEntityManager().remove(entity);
        LOG.info(String.format("Entity successfully deleted. Entity details: %s", entity));
    }

    /**
     * Gets list of entities.
     *
     * @return List.
     */
    @Override
    public List<T> getAll() {
        Query query = getEntityManager()
                .createQuery(String.format("SELECT e FROM %s e", genericClass.getSimpleName()));
        LOG.info("List successfully loaded.");
        return query.getResultList();
    }
}
