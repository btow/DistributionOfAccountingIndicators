package ru.btow.controller;

import org.junit.Before;
import org.junit.Test;
import ru.btow.model.dao.EntityInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AccountingNodesTreeTest extends AccountingNodesTree {

    private final List<EntityInterface> entityList = new ArrayList<>();

    private ConsumptionEntity newConsumptionEntity(UUID uid, String id,
                                                   float consumedVolume, float distributedVolume,
                                                   boolean connectionFlag, UUID parentNodeUid) {
        ConsumptionEntity result = new ConsumptionEntity();
        result.setUUID(uid);
        result.setNodeId(id);
        result.setConsumedVolume(consumedVolume);
        result.setDistributedVolume(distributedVolume);
        result.setConnectionFlag(connectionFlag);
        if (parentNodeUid != null)
            result.setParentNodeUid(parentNodeUid.toString());
        else
            result.setParentNodeUid("");
        return result;
    }

    @Before
    public void setUp() throws Exception {
        super.dataProvider = mock(DataProvider.class);

        ConsumptionEntity testCase1 = newConsumptionEntity(
                UUID.randomUUID(), "1", 1000.0f, 0.0f, true, null);
        entityList.add(testCase1);
        ConsumptionEntity testCase2 = newConsumptionEntity(
                UUID.randomUUID(), "1.1", 300.0f, 101.25f, true, testCase1.getUUID());
        entityList.add(testCase2);
        ConsumptionEntity testCase3 = newConsumptionEntity(
                UUID.randomUUID(), "1.2", 200.0f, 67.5f, true, testCase1.getUUID());
        entityList.add(testCase3);
        ConsumptionEntity testCase4 = newConsumptionEntity(
                UUID.randomUUID(), "1.3", 70.0f, 0.0f, false, testCase1.getUUID());
        entityList.add(testCase4);
        ConsumptionEntity testCase5 = newConsumptionEntity(
                UUID.randomUUID(), "1.4", 300.0f, 101.25f, true, testCase1.getUUID());
        entityList.add(testCase5);
        ConsumptionEntity testCase6 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.1", 150.0f, 0.0f, true, testCase3.getUUID());
        entityList.add(testCase6);
        ConsumptionEntity testCase7 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.2", 50.0f, 0.0f, true, testCase3.getUUID());
        entityList.add(testCase7);
        ConsumptionEntity testCase8 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1", 201.0f, 0.0f, true, testCase5.getUUID());
        entityList.add(testCase8);
        ConsumptionEntity testCase9 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.2", 33.0f, 0.0f, true, testCase5.getUUID());
        entityList.add(testCase9);
        ConsumptionEntity testCase10 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.1", 77.0f, 0.0f, true, testCase8.getUUID());
        entityList.add(testCase10);
        ConsumptionEntity testCase11 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.2", 46.0f, 0.0f, true, testCase8.getUUID());
        entityList.add(testCase11);
        ConsumptionEntity testCase12 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.3", 80.0f, 0.0f, true, testCase8.getUUID());
        entityList.add(testCase12);

        doReturn(entityList).when(super.dataProvider).getAllEntity();
    }

    @Test
    public void testCreateTreeOnRoot() {
        System.out.println("\\======== testCreateTreeOnRoot() =======\\");
        int listSize = this.entityList.size();
        System.out.println("List size: " + listSize);
        System.out.println("\\================ VS ===================\\");
        super.createTreeOnRoot();
        int treeSize = super.accountingNodesTree.size();
        System.out.println("Tree size: " + treeSize);
        assertEquals(listSize, treeSize);
    }

    @Test
    public void testGetNodeOnId() {
        System.out.println("\\======== testGetNodeOnId() =======\\");
        entityList.forEach(entity -> {
            ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
            System.out.println(consumptionEntity);
            System.out.println("\\================ VS ===================\\");
            NodeId nodeId = new NodeId();
            String stringNodeId = consumptionEntity.getNodeId();
            while (stringNodeId.contains(".")){
                int pos = stringNodeId.indexOf(".");
                nodeId.add(Integer.valueOf(stringNodeId.substring(0, pos)));
                stringNodeId = stringNodeId.substring(++pos, stringNodeId.length());
            }
            ConsumptionNode actConsumptionNode = super.getNodeOnId(nodeId);
            System.out.println(actConsumptionNode);
            System.out.println();
            assertEqualsEntityAndNode(consumptionEntity, actConsumptionNode);
        });
    }

    private void assertEqualsEntityAndNode(ConsumptionEntity consumptionEntity, ConsumptionNode actConsumptionNode) {
        assertEquals(consumptionEntity.getNodeId(), actConsumptionNode.getId().toString());
        assertEquals(consumptionEntity.getConsumedVolume(), actConsumptionNode.getConsumedVolume(), 0.0f);
        assertEquals(consumptionEntity.getDistributedVolume(), actConsumptionNode.getDistributedVolume(), 0.0f);
        assertEquals(consumptionEntity.isConnectionFlag(), actConsumptionNode.isConnectionFlag());
        actConsumptionNode.getChildNodes().forEach(node -> {
            assertEquals(consumptionEntity.getNodeId(), super.accountingNodesTree.get(node.getId().toString()).getId().toString());
        });
    }

    @Test
    public void testCreateNewNode() {
        System.out.println("\\======== testCreateNewNode() =======\\");
    }

    @Test
    public void testIsLeaf() {
        System.out.println("\\======== testIsLeaf() =======\\");
    }

    @Test
    public void testGetAmountOfchildrensConsumption() {
        System.out.println("\\======== testGetAmountOfchildrensConsumption() =======\\");
    }

    @Test
    public void testGetDifferenceInConsumptionWithchildren() {
        System.out.println("\\======== testGetDifferenceInConsumptionWithchildren() =======\\");
    }
}