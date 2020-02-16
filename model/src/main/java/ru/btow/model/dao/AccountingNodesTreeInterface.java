package ru.btow.model.dao;

import com.sun.istack.internal.NotNull;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;

/**
 * Created by btow on 15.02.2020.
 */
public interface AccountingNodesTreeInterface {

    ConsumptionNode getTreeRoot();
    ConsumptionNode getNodeOnId(@NotNull NodeId nodeId);
    ConsumptionNode createNewNode(@NotNull ConsumptionNode root);
    boolean isLeaf(@NotNull ConsumptionNode node);
    float getAmountOfchildrensConsumption(@NotNull ConsumptionNode node);
    float getDifferenceInConsumptionWithchildren(@NotNull ConsumptionNode node);
}
