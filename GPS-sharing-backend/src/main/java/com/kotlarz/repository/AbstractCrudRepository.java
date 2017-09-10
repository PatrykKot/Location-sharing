package com.kotlarz.repository;

import com.kotlarz.component.annotation.Init;
import com.kotlarz.database.Database;
import lombok.extern.log4j.Log4j2;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Log4j2
public abstract class AbstractCrudRepository<T> {
    protected Database database;

    protected ObjectRepository<T> repository;

    @Init
    @SuppressWarnings("unchecked")
    public void init() throws ClassNotFoundException {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type generic = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        Class<T> clazz = (Class<T>) Class.forName(generic.getTypeName());

        repository = database.getInstance().getRepository(clazz);
        log.debug("Repository for class " + clazz.getSimpleName() + " initialized");
    }

    @SuppressWarnings("unchecked")
    protected void create(T... objects) {
        repository.insert(objects);
        commit();
    }

    protected Cursor<T> read(ObjectFilter filter) {
        return repository.find(filter);
    }

    protected Cursor<T> read(FindOptions options) {
        return repository.find(options);
    }

    protected Cursor<T> read(ObjectFilter filter, FindOptions options) {
        return repository.find(filter, options);
    }

    @SuppressWarnings("unchecked")
    protected void update(T... objects) {
        for (T obj : objects) {
            repository.update(obj);
        }
        commit();
    }

    @SuppressWarnings("unchecked")
    protected void delete(T... objects) {
        for (T obj : objects) {
            repository.remove(obj);
        }
        commit();
    }

    protected void commit() {
        database.getInstance().commit();
    }
}
