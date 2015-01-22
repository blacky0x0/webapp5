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
public class MapStorage extends AbstractStorage<String> {

    private Map<String, Resume> map = new HashMap<>();

    public void doClear() {
        map.clear();
    }

    @Override
    protected String getContext(String uuid) {
        return uuid;
    }

    protected boolean exist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected void doSave(String ctx, Resume r) {
        map.put(ctx, r);
    }

    @Override
    protected void doUpdate(String ctx, Resume r) {
        map.put(ctx, r);
    }

    @Override
    protected Resume doLoad(String ctx) {
        return map.get(ctx);
    }

    @Override
    protected void doDelete(String ctx) {
        map.remove(ctx);
    }

    public List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    public int size() {
        return map.size();
    }

}
