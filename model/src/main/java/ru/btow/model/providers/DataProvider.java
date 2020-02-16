package ru.btow.model.providers;

import com.sun.istack.internal.NotNull;
import ru.btow.model.dao.DataServiceInterface;
import ru.btow.model.dao.EntityInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by btow on 15.02.2020.
 */
public class DataProvider extends ArrayList<EntityInterface> implements DataServiceInterface {

    @Override
    public DataProvider openConnection(@NotNull String... args) {
        return this;
    }

    @Override
    public void closeConnection() {
    }

    @Override
    public boolean createAnEntity(@NotNull EntityInterface entityInterface) {
        this.add(entityInterface);
        return true;
    }

    @Override
    public EntityInterface readTheAnEntity(@NotNull final UUID uuid) {
        final EntityInterface[] result = {null};
        this.forEach(entity -> {
            if (entity.getUUID().equals(uuid)) result[0] = entity;
        });
        return result[0];
    }

    @Override
    public boolean updateEntity(@NotNull final UUID uuid, @NotNull EntityInterface entityInterface) {
        final boolean[] result = {false};
        this.forEach(entity -> {
            if (entity.getUUID().equals(uuid))
            {
                entity.updateEntity(entityInterface);
                result[0] = true;
                return;
            }
        });
        return result[0];
    }

    @Override
    public boolean deleteTheEntity(@NotNull final UUID uuid) {
        boolean result = false;
        Iterator<EntityInterface> iterator = this.iterator();
        while (iterator.hasNext()){
            EntityInterface entity = iterator.next();
            if (entity.getUUID().equals(uuid)){
                iterator.remove();
                result = true;
                break;
            }
        }
        return result;
    }
}
