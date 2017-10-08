package service.implementation;

import dao.interfaces.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.GenericService;
import java.util.List;

/**
 * Generic service implementation of CRUD entities methods.
 *
 * @param <T>
 */
abstract class GenericServiceImpl<T> implements GenericService<T>{

    private final static Logger LOG = LoggerFactory.getLogger(GenericServiceImpl.class);

    abstract GenericDao<T> getDao();

    /**
     * Creates passed object to the dao layer.
     *
     * @param object new object.
     */
    @Override
    @Transactional
    public void create(T object) {
        getDao().create(object);
        LOG.info(String.format("Entity successfully created. Entity details: %s", object));
    }

    /**
     * Gets entity by passed id to the dao layer.
     *
     * @param id id.
     * @return T.
     */
    @Override
    @Transactional
    public T read(long id) {
        LOG.info(String.format("Entity successfully loaded. Id = '%s'", id));
        return getDao().read(id);
    }

    /**
     * Updates passed object to the dao layer.
     *
     * @param object new object.
     */
    @Override
    @Transactional
    public void update(T object) {
        getDao().update(object);
        LOG.info(String.format("Entity successfully updated. Entity details: %s", object));
    }

    /**
     * Delete object by passed id to the dao layer.
     *
     * @param id id.
     */
    @Override
    @Transactional
    public void delete(long id) {
        getDao().delete(id);
        LOG.info(String.format("Entity successfully deleted. Id = '%s'", id));
    }

    /**
     * Gets list of entities.
     *
     * @return List.
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        LOG.info("List successfully loaded.");
        return getDao().getAll();
    }
}
