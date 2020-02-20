package ru.btow.controller;

import ru.btow.controller.utils.Mathem;
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
    protected Map<String, ConsumptionNode> accountingNodesTree = new TreeMap<>();
    protected Map<String, String> uuidAndIdMap = new TreeMap<>();
    protected Map<String, String> idAndUuidMap = new TreeMap<>();
    protected Map<String, List<String>> idAndMaxAmountListMap = new TreeMap<>();

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
        final float[] result = {0f};
        final float[] maxAmount = {0.0f};
        final List<String>[] maxAmountList = new ArrayList[]{new ArrayList()};
        node.getChildNodes().forEach(childNode -> {
            if (childNode.isConnectionFlag()) {
                if (maxAmount[0] < childNode.getConsumedVolume()){
                    maxAmount[0] = childNode.getConsumedVolume();
                    maxAmountList[0].clear();
                    maxAmountList[0].add(childNode.getId().toString());
                } else if (maxAmount[0] == childNode.getConsumedVolume()){
                    maxAmountList[0].add(childNode.getId().toString());
                }
                result[0] += childNode.getConsumedVolume();
            } else {
                result[0] -= childNode.getConsumedVolume();
            }
        });
        idAndMaxAmountListMap.put(node.getId().toString(), maxAmountList[0]);
        return result[0];
    }

    @Override
    public float getDifferenceInConsumptionWithchildren(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        return (node.getConsumedVolume() + node.getDistributedVolume())
                - getAmountOfChildrensConsumption(node);
    }

    @Override
    public void remaindersDistributionFromRounding(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        // difference in consumption without rounding
        float diffWithoutRounding = node.getConsumedVolume() - getAmountOfChildrensConsumption(node);
        // difference in consumption with rounding
        float diffWithRounding = Mathem.round(diffWithoutRounding, 2);
        float[] differenceOfRound = {diffWithoutRounding - diffWithRounding};
        if (differenceOfRound[0] > 0 && idAndMaxAmountListMap.get(node.getId().toString()).size() > 0){
            differenceOfRound[0] /= (idAndMaxAmountListMap.get(node.getId().toString())).size();
            node.getChildNodes().forEach(childNode -> {
                childNode.setDistributedVolume(differenceOfRound[0]);
            });
        }
    }

    @Override
    public void consumptionDifferencesDistribution(ConsumptionNode node) {
        if (accountingNodesTree.isEmpty()){
            createTreeOnRoot();
        }
        if (isLeaf(node)) return;
        float[] amountOnlyPositiveValues = {0f};
        node.getChildNodes().forEach(childNode -> {
            if (childNode.isConnectionFlag()) amountOnlyPositiveValues[0] += childNode.getConsumedVolume();
        });
        node.getChildNodes().forEach(childNode -> {
            if (childNode.isConnectionFlag())
                childNode.setDistributedVolume(childNode.getDistributedVolume() + (
                    getDifferenceInConsumptionWithchildren(node) * (childNode.getConsumedVolume()/amountOnlyPositiveValues[0])
                    ));
        });
    }

    @Override
    public String getAllLeafNode() {
        final String[] leafNodes = {""};
        accountingNodesTree.forEach((nodeId, consumptionNode) -> {
            if (isLeaf(consumptionNode)){
                leafNodes[0] += ("\nid: " + consumptionNode.getId() + "\n" +
                        "distributedVolume: " + consumptionNode.getDistributedVolume() + "\n");
            }
        });
        return leafNodes[0];
    }
}
