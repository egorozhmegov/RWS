package service.interfaces;

import java.util.List;

/**
 * Generic service of entities.
 *
 * @param <T>
 */
public interface GenericService<T> {
    /**
     * Creates passed object to the dao layer.
     *
     * @param object new object.
     */
    void create(T object);

    /**
     * Gets entity by passed id to the dao layer.
     *
     * @param id id.
     * @return T.
     */
    T read(long id);

    /**
     * Updates passed object to the dao layer.
     *
     * @param object new object.
     */
    void update(T object);

    /**
     * Delete object by passed id to the dao layer.
     *
     * @param id id.
     */
    void delete(long id);

    /**
     * Gets list of entities.
     *
     * @return List.
     */
    List<T> getAll();
}
