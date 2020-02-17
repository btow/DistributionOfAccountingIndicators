package ru.btow.model.dao;

import java.util.List;
import java.util.UUID;

/**
 * Created by btow on 15.02.2020.
 */
public interface DataServiceInterface {

    DataServiceInterface openConnection(String... args);

    void closeConnection();

    List<EntityInterface> getAllEntity();

    boolean createAnEntity(final EntityInterface entityInterface);

    EntityInterface readTheAnEntity(final UUID uuid);

    boolean updateEntity(final UUID uuid, EntityInterface entityInterface);

    boolean deleteTheEntity(final UUID uuid);

}
