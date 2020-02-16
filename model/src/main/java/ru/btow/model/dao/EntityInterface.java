package ru.btow.model.dao;

import java.util.List;
import java.util.UUID;

/**
 * Created by btow on 15.02.2020.
 */
public interface EntityInterface {

    UUID getUUID ();

    void setUUID (UUID uuid);

    void updateEntity(EntityInterface entity);

}
