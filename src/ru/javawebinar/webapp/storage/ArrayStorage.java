package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
//import java.util.logging.Level;


/**
 * GKislin
 * blacky0x0
 * 26.12.2014.
 */
public class ArrayStorage extends AbstractStorage {
    private static final int LIMIT = 100;
    //    protected Logger logger = Logger.getLogger(getClass().getName());
//    private static Logger logger = Logger.getLogger(ArrayStorage.class.getName());

    private Resume[] array = new Resume[LIMIT];
    private int size = 0;

    @Override
    public void doClear() {
        Arrays.fill(array, null);
        size = 0;
    }

    @Override
    protected void doSave(Resume r) {
        if (size == LIMIT)
            throw new IllegalStateException("You can't add a new resume anymore. The limit has been reached.");

        int idx = getIndex(r.getUuid());

        if (idx != -1) throw new WebAppException("Resume " + r.getUuid() + "already exist", r);
        array[size++] = r;
    }

    @Override
    public void doUpdate(Resume r) {
        int idx = getIndex(r.getUuid());
        if (idx == -1) throw new WebAppException("Resume " + r.getUuid() + "not exist", r);
        array[idx] = r;
    }

    @Override
    public Resume doLoad(String uuid) {
        int idx = getIndex(uuid);
        if (idx == -1) throw new WebAppException("Resume " + uuid + "not exist");
        return array[idx];
    }

    @Override
    public void doDelete(String uuid) {
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
        // TODO via comparator
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
