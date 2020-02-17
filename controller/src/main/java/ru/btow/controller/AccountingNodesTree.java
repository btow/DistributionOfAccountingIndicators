package ru.btow.controller;

import ru.btow.model.dao.AccountingNodesTreeInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccountingNodesTree implements AccountingNodesTreeInterface {

    private DataProvider dataProvider;
    private Map<String, ConsumptionNode> accountingNodesTree = new TreeMap<>();

    void openConnection(){
        this.dataProvider = new DataProvider().openConnection();
    }

    @Override
    public void createTreeOnRoot() {
        if (dataProvider == null){
            openConnection();
        }
        dataProvider.getAllEntity().forEach(entity -> {
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
            if (consumptionEntity.getParentNodeUid() != null){
                accountingNodesTree.get(consumptionEntity.getNodeId()).getChildNodes().add(node);
            }
            accountingNodesTree.put(consumptionEntity.getNodeId(), node);
        });
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
