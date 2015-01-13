package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
//import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GKislin
 * blacky0x0
 * 26.12.2014.
 */
public class ArrayStorage implements IStorage {
    private static final int LIMIT = 100;
    //    protected Logger LOGGER = Logger.getLogger(getClass().getName());
    private static Logger LOGGER = Logger.getLogger(ArrayStorage.class.getName());

    private Resume[] array = new Resume[LIMIT];
    private int size = 0;

    @Override
    public void clear() {
        LOGGER.info("Delete all resumes.");
        Arrays.fill(array, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (r == null)
            throw new IllegalStateException("You can't add a null element.");

        if (size == LIMIT)
            throw new IllegalStateException("You can't add a new resume anymore. The limit has been reached.");

        LOGGER.info("Save resume with uuid=" + r.getUuid());
        int idx = getIndex(r.getUuid());
/*
            try {
                throw new WebAppException("Resume " + r.getUuid() + "already exist", r);
            } catch (WebAppException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw new IllegalStateException(e);
            }
*/
        if (idx != -1) throw new WebAppException("Resume " + r.getUuid() + "already exist", r);
        array[size++] = r;
    }

    @Override
    public void update(Resume r) {
        if (r == null)
            throw new IllegalStateException("You can't update to a null element.");

        LOGGER.info("Update resume with " + r.getUuid());
        int idx = getIndex(r.getUuid());
        if (idx == -1) throw new WebAppException("Resume " + r.getUuid() + "not exist", r);
        array[idx] = r;
    }

    @Override
    public Resume load(String uuid) {
        if (uuid == null || uuid.isEmpty())
            throw new IllegalArgumentException("Illegal UUID string.");

        LOGGER.info("Load resume with uuid=" + uuid);
        int idx = getIndex(uuid);
        if (idx == -1) throw new WebAppException("Resume " + uuid + "not exist");
        return array[idx];
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete resume with uuid=" + uuid);
        int idx = getIndex(uuid);
        if (idx == -1) throw new WebAppException("Resume " + uuid + "not exist");
        int numMoved = size - idx - 1;
        if (numMoved > 0)
            System.arraycopy(array, idx+1, array, idx,
                    numMoved);
        array[--size] = null; // clear to let GC do its work
    }

    @Override
    public Collection<Resume> getAllSorted() {
        Arrays.sort(array, 0, size);
        return Arrays.asList(Arrays.copyOf(array, size));
    }

    @Override
    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < LIMIT; i++) {
            if (array[i] != null) {
                if (array[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
