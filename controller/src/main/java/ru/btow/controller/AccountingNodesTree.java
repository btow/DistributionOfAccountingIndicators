package ru.btow.controller;

import ru.btow.model.dao.AccountingNodesTreeInterface;
import ru.btow.model.dao.EntityInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;

import java.util.*;

public class AccountingNodesTree implements AccountingNodesTreeInterface {

    protected DataProvider dataProvider;
    protected Map<String, ConsumptionNode> accountingNodesTree = new HashMap<>();
    protected Map<String, String> uuidAndIdMap = new HashMap<>();

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
            ConsumptionNode node = new ConsumptionNode();
            NodeId nodeId = new NodeId();
            String stringNodeId = consumptionEntity.getNodeId();
            while (stringNodeId.contains(".")){
                int pos = stringNodeId.indexOf(".");
                nodeId.add(Integer.valueOf(stringNodeId.substring(0, pos)));
                stringNodeId = stringNodeId.substring(++pos, stringNodeId.length());
            }
            nodeId.add(Integer.valueOf(stringNodeId));
            node.setId(nodeId);
            node.setConsumedVolume(consumptionEntity.getConsumedVolume());
            node.setDistributedVolume(consumptionEntity.getDistributedVolume());
            node.setConnectionFlag(consumptionEntity.isConnectionFlag());
            node.setChildNodes(new ArrayList<>());
            if (!consumptionEntity.getParentNodeUid().isEmpty()){
                accountingNodesTree.get(uuidAndIdMap.get(consumptionEntity.getParentNodeUid())).getChildNodes().add(node);
            }
            accountingNodesTree.put(consumptionEntity.getNodeId(), node);
            uuidAndIdMap.put(consumptionEntity.getUUID().toString(), consumptionEntity.getNodeId());
        }
    }

    @Override
    public ConsumptionNode getNodeOnId(NodeId nodeId) {
        ConsumptionNode node = new ConsumptionNode();
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return accountingNodesTree.get(nodeId.toString());
    }

    @Override
    public ConsumptionNode getNodeOnUid(UUID nodeUuid) {
        return null;
    }

    @Override
    public ConsumptionNode createNewNode(ConsumptionNode root) {
        return null;
    }

    @Override
    public boolean isLeaf(ConsumptionNode node) {
        return false;
    }

    @Override
    public float getAmountOfchildrensConsumption(ConsumptionNode node) {
        return 0;
    }

    @Override
    public float getDifferenceInConsumptionWithchildren(ConsumptionNode node) {
        return 0;
    }
}
