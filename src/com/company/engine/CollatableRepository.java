package com.company.engine;

import com.company.engine.entities.StaticEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollatableRepository implements Iterable<StaticEntity> {

    private static CollatableRepository instance;
    private final List<StaticEntity> registeredEntities;

    public static CollatableRepository getInstance() {
        if (instance == null) {
            instance = new CollatableRepository();
        }
        return instance;
    }

    public void registeredEntities(Collection<StaticEntity> entities) {
        registeredEntities.addAll(entities);
    }

    public void unregisteredEntity(StaticEntity entity) {
        registeredEntities.remove(entity);
    }

    public void registeredEntity(StaticEntity entity) {
         registeredEntities.add(entity);
    }

    public int count() {
        return registeredEntities.size();
    }

    @Override
    public Iterator<StaticEntity> iterator() {
        return registeredEntities.iterator();
    }

    private CollatableRepository() {
       registeredEntities = new ArrayList<>();
    }
}
