package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.ContactType;
import ru.javawebinar.webapp.model.Resume;

import java.util.*;
import java.util.logging.Logger;

/**
 * GKislin
 * blacky0x0
 * 09.01.2015.
 */
abstract public class AbstractStorage implements IStorage {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void clear() {
        logger.info("Delete all resumes.");
        doClear();
    }

    protected abstract void doClear();

    protected abstract boolean exist(String uuid);

    @Override
    public void save(Resume r) {
        if (r == null)
            throw new WebAppException("You can't add a null element");

        logger.info("Save resume with uuid=" + r.getUuid());
        if (exist(r.getUuid())) {
            throw new WebAppException("Resume " + r.getUuid() + "already exist", r);
        }
        doSave(r);
    }

    protected abstract void doSave(Resume r);

    @Override
    public void update(Resume r) {
        if (r == null)
            throw new WebAppException("You can't update a null element");

        logger.info("Update resume with " + r.getUuid());
        if (!exist(r.getUuid())) {
            throw new WebAppException("Resume " + r.getUuid() + "not exist", r);
        }
        doUpdate(r);
    }

    protected abstract void doUpdate(Resume r);

    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with uuid=" + uuid);
        if (!exist(uuid)) {
            throw new WebAppException("Resume " + uuid + "not exist", uuid);
        }
        return doLoad(uuid);
    }

    protected abstract Resume doLoad(String uuid);

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with uuid=" + uuid);
        if (!exist(uuid)) {
            throw new WebAppException("Resume " + uuid + "not exist", uuid);
        }
        doDelete(uuid);
    }

    protected abstract void doDelete(String uuid);

    @Override
    public Collection<Resume> getAllSorted() {
        logger.info("getAllSorted");
        List<Resume> list = doGetAll();
        Collections.sort(list, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                int cmp = o1.getFullName().compareTo(o2.getFullName());
                if (cmp != 0) return cmp;
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
/*
        Collections.sort(list, (Resume o1, Resume o2) -> {
            int cmp = o1.getFullName().compareTo(o2.getFullName());
            if (cmp != 0) return cmp;
            return o1.getUuid().compareTo(o2.getUuid());
        });
        return Collections.singletonList(new Resume());
*/
        return list;
    }

    protected abstract List<Resume> doGetAll();

    public abstract int size();

}
