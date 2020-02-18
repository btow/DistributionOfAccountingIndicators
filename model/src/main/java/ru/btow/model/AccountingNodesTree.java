package ru.btow.model;

import ru.btow.model.dao.AccountingNodesTreeInterface;
import ru.btow.model.dao.EntityInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;

import java.io.IOException;
import java.util.*;

public class AccountingNodesTree implements AccountingNodesTreeInterface {

    protected DataProvider dataProvider;
    protected Map<String, ConsumptionNode> accountingNodesTree = new HashMap<>();
    protected Map<String, String> uuidAndIdMap = new HashMap<>();
    protected Map<String, String> idAndUuidMap = new HashMap<>();

    void openConnection(){
        this.dataProvider = new DataProvider().openConnection();
    }

    @Override
    public void createTreeOnRoot() {
        if (dataProvider == null){
            openConnection();
        }
        for (EntityInterface entity :
                dataProvider.getAllEntity()) {
            ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
            ConsumptionNode node = new ConsumptionNode(
                    NodeId.nodeIdFromString(consumptionEntity.getNodeId()),
                    consumptionEntity.getConsumedVolume(),
                    consumptionEntity.getDistributedVolume(),
                    consumptionEntity.isConnectionFlag()
            );
            node.setChildNodes(new ArrayList<>());
            if (!consumptionEntity.getParentNodeUid().isEmpty()){
                accountingNodesTree.get(uuidAndIdMap.get(consumptionEntity.getParentNodeUid())).getChildNodes().add(node);
            }
            accountingNodesTree.put(consumptionEntity.getNodeId(), node);
            uuidAndIdMap.put(consumptionEntity.getUUID().toString(), consumptionEntity.getNodeId());
            idAndUuidMap.put(consumptionEntity.getNodeId(), consumptionEntity.getUUID().toString());
        }
    }

    @Override
    public ConsumptionNode getNodeOnId(NodeId nodeId) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return accountingNodesTree.get(nodeId.toString());
    }

    @Override
    public ConsumptionNode getNodeOnUid(UUID nodeUuid) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return accountingNodesTree.get(uuidAndIdMap.get(nodeUuid.toString()));
    }

    @Override
    public ConsumptionNode createNewNode(ConsumptionNode root) throws IOException {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        int childListSize = root.getChildNodes().size();
        NodeId newNodeId = new NodeId();
        newNodeId.addAll(root.getId());
        newNodeId.add(++childListSize);
        if (idAndUuidMap.containsKey(newNodeId.toString()))
            throw new IOException("The node ID was duplicated.");
        String newUid = idAndUuidMap.get(root.getId().toString());
        boolean createNewUid = true;
        while (createNewUid){
            newUid = UUID.randomUUID().toString();
            createNewUid = uuidAndIdMap.containsKey(newUid);
        }
        ConsumptionNode newConsumptionNode = new ConsumptionNode(newNodeId);
        accountingNodesTree.put(newConsumptionNode.getId().toString(), newConsumptionNode);
        uuidAndIdMap.put(newUid, newConsumptionNode.getId().toString());
        idAndUuidMap.put(newConsumptionNode.getId().toString(), newUid);
        root.getChildNodes().add(newConsumptionNode);
        return newConsumptionNode;
    }

    @Override
    public boolean isLeaf(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return node.getChildNodes().isEmpty();
    }

    @Override
    public float getAmountOfChildrensConsumption(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        float[] result = {0f};
        node.getChildNodes().forEach(childNode -> {
            if (childNode.isConnectionFlag())
                result[0] += childNode.getConsumedVolume();
            else
                result[0] -= childNode.getConsumedVolume();
        });
        return result[0];
    }

    @Override
    public float getDifferenceInConsumptionWithchildren(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return 0;
    }
}
