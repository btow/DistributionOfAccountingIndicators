package ru.btow.model.dao;

import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by btow on 15.02.2020.
 */
public interface AccountingNodesTreeInterface {

    void createTreeOnRoot();
    ConsumptionNode getNodeOnId(NodeId nodeId);
    ConsumptionNode getNodeOnUid(UUID nodeUuid);
    ConsumptionNode createNewNode(ConsumptionNode root) throws IOException;
    boolean isLeaf(ConsumptionNode node);
    float getAmountOfChildrensConsumption(ConsumptionNode node);
    float getDifferenceInConsumptionWithchildren(ConsumptionNode node);
}
