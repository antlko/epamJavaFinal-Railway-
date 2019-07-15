package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.exception.DBException;

import java.util.List;

public interface Dao<T> {

    T get(long id);

    List<T> getAll() throws DBException;

    void save(T t) throws DBException;

    void update(T t, String[] params) throws DBException;

    void delete(T t) throws DBException;
}
