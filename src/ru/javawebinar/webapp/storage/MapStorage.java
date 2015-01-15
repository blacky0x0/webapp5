package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.*;

/**
 * GKislin
 * blacky0x0
 * 09.01.2015.
 */
public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void doClear() {
        map.clear();
    }

    @Override
    protected void doSave(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void doUpdate(Resume r) {
        map.replace(r.getUuid(), r);
    }

    @Override
    public Resume doLoad(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    public Collection<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(map.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }
}
