package ru.btow.model;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import ru.btow.model.dao.EntityInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;
import ru.btow.model.utils.Mathem;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountingNodesTreeTest extends AccountingNodesTree {

    private final List<EntityInterface> entityList = new ArrayList<>();
    private final Map<String, Boolean> idIsLeafMap = new HashMap<>();
    private final Map<String, Pair<Float, String[]>> idAmountOfChildMap = new HashMap<>();

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
        doReturn(testCase1).when(super.dataProvider).readTheAnEntity(testCase1.getUUID());
        entityList.add(testCase1);
        idIsLeafMap.put(testCase1.getNodeId(), false);
        String[] idWithMaxConsuption1 = {"1.1", "1.4"};
        Pair<Float, String[]> amountAndIdMaxConsumption1 = new Pair<>(730.0f, idWithMaxConsuption1);
        idAmountOfChildMap.put(testCase1.getNodeId(), amountAndIdMaxConsumption1);

        ConsumptionEntity testCase2 = newConsumptionEntity(
                UUID.randomUUID(), "1.1", 300.0f, 101.25f, true, testCase1.getUUID());
        doReturn(testCase2).when(super.dataProvider).readTheAnEntity(testCase2.getUUID());
        entityList.add(testCase2);
        idIsLeafMap.put(testCase2.getNodeId(), true);
        String[] idWithMaxConsuption2 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption2 = new Pair<>(0.0f, idWithMaxConsuption2);
        idAmountOfChildMap.put(testCase2.getNodeId(), amountAndIdMaxConsumption2);

        ConsumptionEntity testCase3 = newConsumptionEntity(
                UUID.randomUUID(), "1.2", 200.0f, 67.5f, true, testCase1.getUUID());
        doReturn(testCase3).when(super.dataProvider).readTheAnEntity(testCase3.getUUID());
        entityList.add(testCase3);
        idIsLeafMap.put(testCase3.getNodeId(), false);
        String[] idWithMaxConsuption3 = {"1.2.1"};
        Pair<Float, String[]> amountAndIdMaxConsumption3 = new Pair<>(200.0f, idWithMaxConsuption3);
        idAmountOfChildMap.put(testCase3.getNodeId(), amountAndIdMaxConsumption3);

        ConsumptionEntity testCase4 = newConsumptionEntity(
                UUID.randomUUID(), "1.3", 70.0f, 0.0f, false, testCase1.getUUID());
        doReturn(testCase4).when(super.dataProvider).readTheAnEntity(testCase4.getUUID());
        entityList.add(testCase4);
        idIsLeafMap.put(testCase4.getNodeId(), true);
        String[] idWithMaxConsuption4 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption4 = new Pair<>(0.0f, idWithMaxConsuption4);
        idAmountOfChildMap.put(testCase4.getNodeId(), amountAndIdMaxConsumption4);

        ConsumptionEntity testCase5 = newConsumptionEntity(
                UUID.randomUUID(), "1.4", 300.0f, 101.25f, true, testCase1.getUUID());
        doReturn(testCase5).when(super.dataProvider).readTheAnEntity(testCase5.getUUID());
        entityList.add(testCase5);
        idIsLeafMap.put(testCase5.getNodeId(), false);
        String[] idWithMaxConsuption5 = {"1.4.1"};
        Pair<Float, String[]> amountAndIdMaxConsumption5 = new Pair<>(234.0f, idWithMaxConsuption5);
        idAmountOfChildMap.put(testCase5.getNodeId(), amountAndIdMaxConsumption5);

        ConsumptionEntity testCase6 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.1", 150.0f, 0.0f, true, testCase3.getUUID());
        doReturn(testCase6).when(super.dataProvider).readTheAnEntity(testCase6.getUUID());
        entityList.add(testCase6);
        idIsLeafMap.put(testCase6.getNodeId(), true);
        String[] idWithMaxConsuption6 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption6 = new Pair<>(0.0f, idWithMaxConsuption6);
        idAmountOfChildMap.put(testCase6.getNodeId(), amountAndIdMaxConsumption6);

        ConsumptionEntity testCase7 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.2", 50.0f, 0.0f, true, testCase3.getUUID());
        doReturn(testCase7).when(super.dataProvider).readTheAnEntity(testCase7.getUUID());
        entityList.add(testCase7);
        idIsLeafMap.put(testCase7.getNodeId(), true);
        String[] idWithMaxConsuption7 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption7 = new Pair<>(0.0f, idWithMaxConsuption7);
        idAmountOfChildMap.put(testCase7.getNodeId(), amountAndIdMaxConsumption7);

        ConsumptionEntity testCase8 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1", 201.0f, 0.0f, true, testCase5.getUUID());
        doReturn(testCase8).when(super.dataProvider).readTheAnEntity(testCase8.getUUID());
        entityList.add(testCase8);
        idIsLeafMap.put(testCase8.getNodeId(), false);
        String[] idWithMaxConsuption8 = {"1.4.1.3"};
        Pair<Float, String[]> amountAndIdMaxConsumption8 = new Pair<>(203.0f, idWithMaxConsuption8);
        idAmountOfChildMap.put(testCase8.getNodeId(), amountAndIdMaxConsumption8);

        ConsumptionEntity testCase9 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.2", 33.0f, 0.0f, true, testCase5.getUUID());
        doReturn(testCase9).when(super.dataProvider).readTheAnEntity(testCase9.getUUID());
        entityList.add(testCase9);
        idIsLeafMap.put(testCase9.getNodeId(), true);
        String[] idWithMaxConsuption9 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption9 = new Pair<>(0.0f, idWithMaxConsuption9);
        idAmountOfChildMap.put(testCase9.getNodeId(), amountAndIdMaxConsumption9);

        ConsumptionEntity testCase10 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.1", 77.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase10).when(super.dataProvider).readTheAnEntity(testCase10.getUUID());
        entityList.add(testCase10);
        idIsLeafMap.put(testCase10.getNodeId(), true);
        String[] idWithMaxConsuption10 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption10 = new Pair<>(0.0f, idWithMaxConsuption10);
        idAmountOfChildMap.put(testCase10.getNodeId(), amountAndIdMaxConsumption10);

        ConsumptionEntity testCase11 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.2", 46.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase11).when(super.dataProvider).readTheAnEntity(testCase11.getUUID());
        entityList.add(testCase11);
        idIsLeafMap.put(testCase11.getNodeId(), true);
        String[] idWithMaxConsuption11 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption11 = new Pair<>(0.0f, idWithMaxConsuption11);
        idAmountOfChildMap.put(testCase11.getNodeId(), amountAndIdMaxConsumption11);

        ConsumptionEntity testCase12 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.3", 80.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase12).when(super.dataProvider).readTheAnEntity(testCase12.getUUID());
        entityList.add(testCase12);
        idIsLeafMap.put(testCase12.getNodeId(), true);
        String[] idWithMaxConsuption12 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption12 = new Pair<>(0.0f, idWithMaxConsuption12);
        idAmountOfChildMap.put(testCase12.getNodeId(), amountAndIdMaxConsumption12);

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

    private void assertEqualsEntityAndNode(ConsumptionEntity consumptionEntity, ConsumptionNode actConsumptionNode) {
        assertEquals(consumptionEntity.getNodeId(), actConsumptionNode.getId().toString());
        assertEquals(consumptionEntity.getConsumedVolume(), actConsumptionNode.getConsumedVolume(), 0.0f);
        assertEquals(consumptionEntity.getDistributedVolume(), actConsumptionNode.getDistributedVolume(), 0.0f);
        assertEquals(consumptionEntity.isConnectionFlag(), actConsumptionNode.isConnectionFlag());
        actConsumptionNode.getChildNodes().forEach(node -> {
            UUID uuidOnId = UUID.fromString(super.idAndUuidMap.get(node.getId().toString()));
            ConsumptionEntity childEntity = (ConsumptionEntity) super.dataProvider.readTheAnEntity(uuidOnId);
            assertEquals(super.uuidAndIdMap.get(childEntity.getParentNodeUid()),
                    actConsumptionNode.getId().toString());
        });
    }

    @Test
    public void testGetNodeOnId() {
        System.out.println("\\======== testGetNodeOnId() =======\\");
        entityList.forEach(entity -> {
            ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
            System.out.println(consumptionEntity);
            System.out.println("\\================ VS ===================\\");
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(consumptionEntity.getNodeId()));
            System.out.println(actConsumptionNode);
            System.out.println();
            assertEqualsEntityAndNode(consumptionEntity, actConsumptionNode);
        });
    }

    @Test
    public void testCreateNewNode() {
        System.out.println("\\======== testCreateNewNode() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        this.entityList.forEach(entity -> {
            ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
            int listSize = this.entityList.size();
            System.out.println("List size: " + listSize);
            System.out.println(super.accountingNodesTree.get(consumptionEntity.getNodeId()));
            ConsumptionNode rootNode = super.getNodeOnId(NodeId.nodeIdFromString(consumptionEntity.getNodeId()));
            ConsumptionNode newNode = null;
            try {
                newNode = this.createNewNode(rootNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int treeSize = super.accountingNodesTree.size();
            System.out.println("Tree size: " + treeSize);
            System.out.println(super.accountingNodesTree.get(consumptionEntity.getNodeId()));
            assertTrue(listSize < treeSize);
        });
    }

    @Test
    public void testIsLeaf() {
        System.out.println("\\======== testIsLeaf() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        idIsLeafMap.forEach((id, isLeaf) -> {
            ConsumptionNode testNode = super.getNodeOnId(NodeId.nodeIdFromString(id));
            System.out.println("Node with id \"" + id + "\" is" + (isLeaf ? " " : " not ") + "leaf.");
            assertEquals(isLeaf, super.isLeaf(testNode));
        });
    }

    private void assertEqualsArrayAndList(String[] expValues, List<String> actValues) {
        assertEquals(expValues.length, actValues.size());
        int[] i = {0};
        actValues.forEach(actValue -> {
            assertEquals(expValues[i[0]++], actValue);
        });
    }

    @Test
    public void testGetAmountOfChildrensConsumption() {
        System.out.println("\\======== testGetAmountOfChildrensConsumption() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        idAmountOfChildMap.forEach((nodeId, expAmountOfChild) -> {
            System.out.println("The expected amount of consumption of the children of the node " +
                    "with the ID \"" + nodeId + "\"is equal to \"" + expAmountOfChild.getKey() + "\".\n");
            System.out.println("\\================ VS ===================\\");
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(nodeId));
            float actAmountOfChild = super.getAmountOfChildrensConsumption(actConsumptionNode);
            System.out.println("The actual amount of consumption of the children of the node " +
                    "with the ID \"" + nodeId + "\"is equal to \"" + actAmountOfChild + "\".\n");
            assertEquals(expAmountOfChild.getKey(), actAmountOfChild, 0.0f);
            System.out.println(Arrays.toString(expAmountOfChild.getValue()));
            System.out.println("\\================ VS ===================\\");
            System.out.println(super.idAndMaxAmountListMap.get(nodeId).toString());
            assertEqualsArrayAndList(expAmountOfChild.getValue(), super.idAndMaxAmountListMap.get(nodeId));
        });
    }

    @Test
    public void testGetDifferenceInConsumptionWithchildren() {
        System.out.println("\\======== testGetDifferenceInConsumptionWithchildren() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        entityList.forEach(entity -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(expConsumptionEntity.getNodeId()));
            float expDifference = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                    - idAmountOfChildMap.get(expConsumptionEntity.getNodeId()).getKey();
            System.out.println("Expected difference is " + String.valueOf(expDifference) + ".");
            float actDifference = super.getDifferenceInConsumptionWithchildren(actConsumptionNode);
            System.out.println("Actual difference is " + String.valueOf(actDifference) + ".");
            System.out.println();
            assertEquals(expDifference, actDifference, 0.0f);
        });
    }

    @Test
    public void testRemaindersDistributionFromRounding() {
        System.out.println("\\======== testRemaindersDistributionFromRounding() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        super.accountingNodesTree.forEach((nodeId, consumptionNode) -> {
            consumptionNode.setDistributedVolume(0.0f);
        });
        entityList.forEach(entity -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            float expDifferenceWithoutRound = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                    - idAmountOfChildMap.get(expConsumptionEntity.getNodeId()).getKey();
            float expDifferenceWithRound = Mathem.round(expDifferenceWithoutRound, 2);
            final float[] expDifferenceOfRound = {expDifferenceWithoutRound - expDifferenceWithRound};
            String[] expMaxAmountList = idAmountOfChildMap.get(expConsumptionEntity.getNodeId()).getValue();
            System.out.println("Test entity: " + entity);
            if(expMaxAmountList.length > 0 && expDifferenceOfRound[0] > 0) {
                expDifferenceOfRound[0] = expDifferenceOfRound[0]/expMaxAmountList.length;
                System.out.println("The expected difference between the full and rounded values: "
                        + expDifferenceOfRound);
                System.out.println("It is expected that it should be assigned to a node with the ID " +
                        Arrays.toString(expMaxAmountList));
                System.out.println("\\================ VS ===================\\");
            }

            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(expConsumptionEntity.getNodeId()));
            super.remaindersDistributionFromRounding(actConsumptionNode);
            System.out.println("\\=================================\\\nActual node: " + actConsumptionNode);

            List<String> nodeIdOfMaxAmountChildList = super.idAndMaxAmountListMap.get(expConsumptionEntity.getNodeId());
            if (nodeIdOfMaxAmountChildList != null){
                nodeIdOfMaxAmountChildList.forEach(nodeIdOfMaxAmountChild -> {
                    float actDifferenceOfRound = super.getNodeOnId(NodeId.nodeIdFromString(nodeIdOfMaxAmountChild))
                            .getDistributedVolume();
                    System.out.println("The actual difference between the full and rounded values: " + actDifferenceOfRound);
                    assertEquals(expDifferenceOfRound[0], actDifferenceOfRound, 0.0f);
                });
                System.out.println("It is expected that it should be assigned to a node with the ID \"" +
                        nodeIdOfMaxAmountChildList);
                assertEqualsArrayAndList(expMaxAmountList, nodeIdOfMaxAmountChildList);
            }
        });
    }

    @Test
    public void testConsumptionDifferencesDistribution() {
        System.out.println("\\======== testConsumptionDifferencesDistribution() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();
        super.accountingNodesTree.forEach((nodeId, consumptionNode) -> {
            consumptionNode.setDistributedVolume(0.0f);
        });
        entityList.forEach(entity -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            float expDifferenceWithoutRound = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                    - idAmountOfChildMap.get(expConsumptionEntity.getNodeId()).getKey();
            float expDifferenceWithRound = Mathem.round(expDifferenceWithoutRound, 2);

        });
    }

}