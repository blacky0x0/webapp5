package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * blacky0x0
 * 09.01.2015.
 */
public class MapStorage {//extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();

    public void doClear() {
        map.clear();
    }

    protected boolean exist(String uuid) {
        return map.containsKey(uuid);
    }

    public void doSave(Resume r) {
        map.put(r.getUuid(), r);
    }

    public void doUpdate(Resume r) {
        map.put(r.getUuid(), r);
    }

    public Resume doLoad(String uuid) {
        return map.get(uuid);
    }

    public void doDelete(String uuid) {
        map.remove(uuid);
    }

    public List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    public int size() {
        return map.size();
    }

}
