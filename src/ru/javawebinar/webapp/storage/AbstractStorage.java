package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.logging.Logger;

/**
 * GKislin
 * 09.01.2015.
 */
abstract public class AbstractStorage implements IStorage {
    protected Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void save(Resume r) {
        logger.info("Save resume with uuid=" + r.getUuid());
        // TODO try to move here exception treatment
        doSave(r);
    }

    protected abstract void doSave(Resume r);

}
