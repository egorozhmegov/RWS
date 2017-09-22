package dao.interfaces;

import java.util.List;

/*
Generic dao interface. Include CRUD methods for all entities.
 */

public interface GenericDao<T> {

    void create(T entity);

    T read(int id);

    void update(T entity);

    void delete(int id);

    List<T> getAll();
}
