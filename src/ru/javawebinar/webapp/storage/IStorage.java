package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.Collection;

/**
 * GKislin
 * 26.12.2014.
 */
public interface IStorage {
    void clear();

    void save(Resume r) throws WebAppException;

    void update(Resume r) throws WebAppException;

    Resume load(String uuid) throws WebAppException;

    void delete(String uuid) throws WebAppException;

    Collection<Resume> getAllSorted();

    int size();
}
