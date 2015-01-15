package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.logging.Logger;

/**
 * GKislin
 * blacky0x0
 * 09.01.2015.
 */
abstract public class AbstractStorage implements IStorage {
    protected Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void clear() {
        logger.info("Delete all resumes.");
        doClear();
    }

    @Override
    public void save(Resume r) {
        if (r == null)
            throw new WebAppException("You can't add a null element");

        logger.info("Save resume with uuid = " + r.getUuid());
        // TODO try to move here exception treatment

        if (!isExist(r)) {
            doSave(r);
            return;
        }

        throw new WebAppException("Resume " + r.getUuid() + " already exist", r);
    }

    @Override
    public void update(Resume r) {
        if (r == null)
            throw new WebAppException("You can't update to a null element");

        logger.info("Update resume with uuid = " + r.getUuid());

        if (isExist(r)) {
            doUpdate(r);
            return;
        }

        throw new WebAppException("Resume " + r.getUuid() + " not exist");
    }

    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with uuid=" + uuid);

        if (isExist(uuid))
            return doLoad(uuid);

        throw new WebAppException("Can't load the resume", uuid);
    }

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with uuid=" + uuid);

        if (isExist(uuid)) {
            doDelete(uuid);
            return;
        }

        throw new WebAppException("Can't delete the resume", uuid);
    }


    protected abstract void doClear();

    protected abstract void doSave(Resume r);

    protected abstract void doUpdate(Resume r);

    protected abstract Resume doLoad(String uuid);

    protected abstract void doDelete(String uuid);

    protected abstract boolean isExist(Resume resume);

    protected abstract boolean isExist(String uuid);
}
