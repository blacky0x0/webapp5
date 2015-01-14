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

        try { doSave(r); }
        catch (RuntimeException e) {
            throw new WebAppException("Can't save the resume", r, e);
        }
    }

    @Override
    public void update(Resume r) {
        if (r == null)
            throw new WebAppException("You can't update to a null element");

        logger.info("Update resume with uuid = " + r.getUuid());

        try { doUpdate(r); }
        catch (RuntimeException e) {
            throw new WebAppException("Can't update the resume", r, e);
        }
    }

    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with uuid=" + uuid);
        Resume result;

        try { result = doLoad(uuid); }
        catch (RuntimeException e) {
            throw new WebAppException("Can't load the resume", uuid, e);
        }

        return result;
    }

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with uuid=" + uuid);

        try { doDelete(uuid); }
        catch (RuntimeException e) {
            throw new WebAppException("Can't delete the resume", uuid, e);
        }
    }


    protected abstract void doClear();

    protected abstract void doSave(Resume r);

    protected abstract void doUpdate(Resume r);

    protected abstract Resume doLoad(String uuid);

    protected abstract void doDelete(String uuid);

}
