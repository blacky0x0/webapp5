package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: blacky
 * Date: 08.02.15
 */
public class ConcurrentMapStorage extends AbstractStorage<String> {

    private Map<String, Resume> MAP = new ConcurrentHashMap<>();

    @Override
    protected String getContext(String uuid) {
        return uuid;
    }

    @Override
    protected boolean exist(String uuid) {
        return MAP.containsKey(uuid);
    }

    @Override
    protected void doClear() {
        MAP.clear();
    }

    @Override
    protected void doSave(String uuid, Resume r) {
        MAP.putIfAbsent(uuid, r);
    }

    @Override
    protected void doUpdate(String uuid, Resume r) {
        MAP.putIfAbsent(uuid, r);
    }

    @Override
    protected Resume doLoad(String uuid) {
        return MAP.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        MAP.remove(uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(MAP.values());
    }

    @Override
    public int size() {
        return MAP.size();
    }
}
