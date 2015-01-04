package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * GKislin
 * blacky0x0
 * 26.12.2014.
 */
public class ArrayStorage implements IStorage {
    private static final int LIMIT = 100;
    private int size = 0;

    private Resume[] array = new Resume[LIMIT];

    @Override
    public void clear() {
        for (int i = 0; i < LIMIT; ++i)
            array[i] = null;

        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (r == null)
            throw new IllegalStateException("You can't add a null element.");

        if (size == LIMIT)
            throw new IllegalStateException("You can't add a new resume anymore. The limit has been reached.");

        for (int i = 0; i < size; i++)
            if (r.equals(array[i]))
                throw new IllegalStateException("Already present");

        array[size] = r;

        size++;
    }

    @Override
    public void update(Resume r) {
        if (r == null)
            throw new IllegalStateException("You can't update to a null element.");

        for (int i = 0; i < size; i++)
            if (r.equals(array[i]))
                array[i] = r;
    }

    @Override
    public Resume load(String uuid) {
        Resume result = null;

        if (uuid == null || uuid.isEmpty())
            throw new IllegalArgumentException("Illegal UUID string.");

        for (int i = 0; i < size; i++)
            if (array[i].getUuid().equals(uuid))
                result = array[i];

        return result;
    }

    @Override
    public void delete(String uuid) {
        if (uuid == null || uuid.isEmpty())
            throw new IllegalArgumentException("Illegal UUID string.");

        int index = -1;

        for (int i = 0; i < size; i++)
            if (array[i].getUuid().equals(uuid)) {
                array[i] = null;
                index = i;
            }

        if (index == -1)
            return;

        // It's last element?
        if (index == size - 1) {
            size--;
            return;
        }

        // Replace the last element to index position
        array[index] = array[size - 1];
        array[size - 1] = null;
        size--;
    }

    @Override
    public Collection<Resume> getAllSorted() {
        ArrayList<Resume> sortedList = new ArrayList<>();

        for (int i = 0; i < size; i++)
            sortedList.add(array[i]);

        Collections.sort(sortedList);

        return sortedList;
    }

    @Override
    public int size() {
        return size;
    }
}
