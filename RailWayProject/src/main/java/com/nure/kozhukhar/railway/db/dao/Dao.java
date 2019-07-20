package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.exception.DBException;

import java.util.List;

/**
 * DAO interface with basic method which necessary in order to do
 * basic operations with object
 *
 * @param <T>
 * @author Anatol Kozhukhar
 */
public interface Dao<T> {

    /**
     * Get Object By id value.
     *
     * @param id
     * @return
     */
    T get(long id);

    /**
     * Get all objects "T".
     *
     * @return List<T>
     * @throws DBException
     */
    List<T> getAll() throws DBException;

    /**
     * Save new object.
     *
     * @param t
     * @throws DBException
     */
    void save(T t) throws DBException;

    /**
     * Update object
     *
     * @param t Object with prepared information to update
     * @param params other parameters
     * @throws DBException
     */
    void update(T t, String[] params) throws DBException;

    /**
     * Delete object
     *
     * @param t
     * @throws DBException
     */
    void delete(T t) throws DBException;
}
