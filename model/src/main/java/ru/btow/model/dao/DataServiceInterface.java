package ru.btow.model.dao;

import com.sun.istack.internal.NotNull;

import java.util.UUID;

/**
 * Created by btow on 15.02.2020.
 */
public interface DataServiceInterface {

    DataServiceInterface openConnection(String... args);

    void closeConnection();

    boolean createAnEntity(@NotNull final EntityInterface entityInterface);

    EntityInterface readTheAnEntity(@NotNull final UUID uuid);

    boolean updateEntity(@NotNull final UUID uuid, @NotNull EntityInterface entityInterface);

    boolean deleteTheEntity(@NotNull final UUID uuid);

}
