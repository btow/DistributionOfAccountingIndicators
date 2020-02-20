package ru.btow.controller;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import ru.btow.controller.utils.Mathem;
import ru.btow.model.dao.EntityInterface;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;
import ru.btow.model.entity.ConsumptionEntity;
import ru.btow.model.providers.DataProvider;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountingNodesTreeTest extends AccountingNodesTree {

    private final List<EntityInterface> entityList = new ArrayList<>();
    private final Map<String, EntityInterface> idAndEntityMap = new HashMap<>();
    private final Map<String, List<String>> idAndChildListMap = new HashMap<>();
    private final Map<String, Boolean> idIsLeafMap = new HashMap<>();
    private final Map<String, Pair<Float, String[]>> idAndAmountAndMaxChildsMap = new HashMap<>();

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
        List<String> testCase1Childs = new ArrayList<>();
        entityList.add(testCase1);
        idAndEntityMap.put(testCase1.getNodeId(), testCase1);
        idIsLeafMap.put(testCase1.getNodeId(), false);

        ConsumptionEntity testCase2 = newConsumptionEntity(
                UUID.randomUUID(), "1.1", 300.0f, 101.25f, true, testCase1.getUUID());
        doReturn(testCase2).when(super.dataProvider).readTheAnEntity(testCase2.getUUID());
        testCase1Childs.add(testCase2.getNodeId());
        entityList.add(testCase2);
        idAndEntityMap.put(testCase2.getNodeId(), testCase2);
        idIsLeafMap.put(testCase2.getNodeId(), true);

        ConsumptionEntity testCase3 = newConsumptionEntity(
                UUID.randomUUID(), "1.2", 200.0f, 67.5f, true, testCase1.getUUID());
        doReturn(testCase3).when(super.dataProvider).readTheAnEntity(testCase3.getUUID());
        testCase1Childs.add(testCase3.getNodeId());
        List<String> testCase3Childs = new ArrayList<>();
        entityList.add(testCase3);
        idAndEntityMap.put(testCase3.getNodeId(), testCase3);
        idIsLeafMap.put(testCase3.getNodeId(), false);

        ConsumptionEntity testCase4 = newConsumptionEntity(
                UUID.randomUUID(), "1.3", 70.0f, 0.0f, false, testCase1.getUUID());
        doReturn(testCase4).when(super.dataProvider).readTheAnEntity(testCase4.getUUID());
        testCase1Childs.add(testCase4.getNodeId());
        entityList.add(testCase4);
        idAndEntityMap.put(testCase4.getNodeId(), testCase4);
        idIsLeafMap.put(testCase4.getNodeId(), true);

        ConsumptionEntity testCase5 = newConsumptionEntity(
                UUID.randomUUID(), "1.4", 300.0f, 101.25f, true, testCase1.getUUID());
        doReturn(testCase5).when(super.dataProvider).readTheAnEntity(testCase5.getUUID());
        testCase1Childs.add(testCase5.getNodeId());
        idAndChildListMap.put(testCase1.getNodeId(), testCase1Childs);
        List<String> testCase5Childs = new ArrayList<>();
        entityList.add(testCase5);
        idAndEntityMap.put(testCase5.getNodeId(), testCase5);
        idIsLeafMap.put(testCase5.getNodeId(), false);

        ConsumptionEntity testCase6 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.1", 150.0f, 0.0f, true, testCase3.getUUID());
        doReturn(testCase6).when(super.dataProvider).readTheAnEntity(testCase6.getUUID());
        testCase3Childs.add(testCase6.getNodeId());
        entityList.add(testCase6);
        idAndEntityMap.put(testCase6.getNodeId(), testCase6);
        idIsLeafMap.put(testCase6.getNodeId(), true);

        ConsumptionEntity testCase7 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.2", 50.0f, 0.0f, true, testCase3.getUUID());
        doReturn(testCase7).when(super.dataProvider).readTheAnEntity(testCase7.getUUID());
        testCase3Childs.add(testCase7.getNodeId());
        entityList.add(testCase7);
        idAndChildListMap.put(testCase3.getNodeId(), testCase3Childs);
        idAndEntityMap.put(testCase7.getNodeId(), testCase7);
        idIsLeafMap.put(testCase7.getNodeId(), true);

        ConsumptionEntity testCase8 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1", 201.0f, 0.0f, true, testCase5.getUUID());
        doReturn(testCase8).when(super.dataProvider).readTheAnEntity(testCase8.getUUID());
        testCase5Childs.add(testCase8.getNodeId());
        List<String> testCase8Childs = new ArrayList<>();
        entityList.add(testCase8);
        idAndEntityMap.put(testCase8.getNodeId(), testCase8);
        idIsLeafMap.put(testCase8.getNodeId(), false);

        ConsumptionEntity testCase9 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.2", 33.0f, 0.0f, true, testCase5.getUUID());
        doReturn(testCase9).when(super.dataProvider).readTheAnEntity(testCase9.getUUID());
        testCase5Childs.add(testCase9.getNodeId());
        idAndChildListMap.put(testCase5.getNodeId(), testCase5Childs);
        entityList.add(testCase9);
        idAndEntityMap.put(testCase9.getNodeId(), testCase9);
        idIsLeafMap.put(testCase9.getNodeId(), true);

        ConsumptionEntity testCase10 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.1", 77.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase10).when(super.dataProvider).readTheAnEntity(testCase10.getUUID());
        testCase8Childs.add(testCase10.getNodeId());
        entityList.add(testCase10);
        idAndEntityMap.put(testCase10.getNodeId(), testCase10);
        idIsLeafMap.put(testCase10.getNodeId(), true);

        ConsumptionEntity testCase11 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.2", 46.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase11).when(super.dataProvider).readTheAnEntity(testCase11.getUUID());
        testCase8Childs.add(testCase11.getNodeId());
        entityList.add(testCase11);
        idAndEntityMap.put(testCase11.getNodeId(), testCase11);
        idIsLeafMap.put(testCase11.getNodeId(), true);

        ConsumptionEntity testCase12 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.3", 80.0f, 0.0f, true, testCase8.getUUID());
        doReturn(testCase12).when(super.dataProvider).readTheAnEntity(testCase12.getUUID());
        testCase8Childs.add(testCase12.getNodeId());
        idAndChildListMap.put(testCase8.getNodeId(), testCase8Childs);
        entityList.add(testCase12);
        idAndEntityMap.put(testCase12.getNodeId(), testCase12);
        idIsLeafMap.put(testCase12.getNodeId(), true);

        String[] idWithMaxConsuption1 = {testCase2.getNodeId(), testCase5.getNodeId()};
        Pair<Float, String[]> amountAndIdMaxConsumption1 = new Pair<>(
                testCase2.getConsumedVolume() + testCase3.getConsumedVolume() - testCase4.getConsumedVolume() + testCase5.getConsumedVolume(),
                idWithMaxConsuption1);
        idAndAmountAndMaxChildsMap.put(testCase1.getNodeId(), amountAndIdMaxConsumption1);

        String[] idWithMaxConsuption2 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption2 = new Pair<>(0.0f, idWithMaxConsuption2);
        idAndAmountAndMaxChildsMap.put(testCase2.getNodeId(), amountAndIdMaxConsumption2);

        String[] idWithMaxConsuption3 = {testCase6.getNodeId()};
        Pair<Float, String[]> amountAndIdMaxConsumption3 = new Pair<>(
                testCase6.getConsumedVolume() + testCase7.getConsumedVolume(), idWithMaxConsuption3);
        idAndAmountAndMaxChildsMap.put(testCase3.getNodeId(), amountAndIdMaxConsumption3);

        String[] idWithMaxConsuption4 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption4 = new Pair<>(0.0f, idWithMaxConsuption4);
        idAndAmountAndMaxChildsMap.put(testCase4.getNodeId(), amountAndIdMaxConsumption4);

        String[] idWithMaxConsuption5 = {testCase8.getNodeId()};
        Pair<Float, String[]> amountAndIdMaxConsumption5 = new Pair<>(
                testCase8.getConsumedVolume() + testCase9.getConsumedVolume(), idWithMaxConsuption5);
        idAndAmountAndMaxChildsMap.put(testCase5.getNodeId(), amountAndIdMaxConsumption5);

        String[] idWithMaxConsuption6 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption6 = new Pair<>(0.0f, idWithMaxConsuption6);
        idAndAmountAndMaxChildsMap.put(testCase6.getNodeId(), amountAndIdMaxConsumption6);

        String[] idWithMaxConsuption7 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption7 = new Pair<>(0.0f, idWithMaxConsuption7);
        idAndAmountAndMaxChildsMap.put(testCase7.getNodeId(), amountAndIdMaxConsumption7);

        String[] idWithMaxConsuption8 = {testCase12.getNodeId()};
        Pair<Float, String[]> amountAndIdMaxConsumption8 = new Pair<>(
                testCase10.getConsumedVolume() + testCase11.getConsumedVolume() + testCase12.getConsumedVolume(), idWithMaxConsuption8);
        idAndAmountAndMaxChildsMap.put(testCase8.getNodeId(), amountAndIdMaxConsumption8);

        String[] idWithMaxConsuption9 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption9 = new Pair<>(0.0f, idWithMaxConsuption9);
        idAndAmountAndMaxChildsMap.put(testCase9.getNodeId(), amountAndIdMaxConsumption9);

        String[] idWithMaxConsuption10 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption10 = new Pair<>(0.0f, idWithMaxConsuption10);
        idAndAmountAndMaxChildsMap.put(testCase10.getNodeId(), amountAndIdMaxConsumption10);

        String[] idWithMaxConsuption11 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption11 = new Pair<>(0.0f, idWithMaxConsuption11);
        idAndAmountAndMaxChildsMap.put(testCase11.getNodeId(), amountAndIdMaxConsumption11);

        String[] idWithMaxConsuption12 = {};
        Pair<Float, String[]> amountAndIdMaxConsumption12 = new Pair<>(0.0f, idWithMaxConsuption12);
        idAndAmountAndMaxChildsMap.put(testCase12.getNodeId(), amountAndIdMaxConsumption12);

        doReturn(entityList).when(super.dataProvider).getAllEntity();
    }

    @Test
    public void testCreateTreeOnRoot() {
        System.out.println("\\======== testCreateTreeOnRoot() =======\\");
        int listSize = this.idAndEntityMap.size();
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
        idAndEntityMap.forEach((nodeId, entity) -> {
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
        this.idAndEntityMap.forEach((nodeId, entity) -> {
            ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
            int listSize = this.idAndEntityMap.size();
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
        idAndAmountAndMaxChildsMap.forEach((nodeId, expAmountOfChild) -> {
            System.out.println("\nThe expected amount of consumption of the children of the node " +
                    "with the ID \"" + nodeId + "\" is equal to \"" + expAmountOfChild.getKey() + "\".");
            System.out.println("\\================ VS ===================\\");
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(nodeId));
            float actAmountOfChild = super.getAmountOfChildrensConsumption(actConsumptionNode);
            System.out.println("The actual amount of consumption of the children of the node " +
                    "with the ID \"" + nodeId + "\" is equal to \"" + actAmountOfChild + "\".\n");
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
        idAndEntityMap.forEach((nodeId, entity) -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(expConsumptionEntity.getNodeId()));
            float expDifference = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                    - idAndAmountAndMaxChildsMap.get(expConsumptionEntity.getNodeId()).getKey();
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
        idAndEntityMap.forEach((nodeId, entity) -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            float expDifferenceWithoutRound = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                    - idAndAmountAndMaxChildsMap.get(expConsumptionEntity.getNodeId()).getKey();
            float expDifferenceWithRound = Mathem.round(expDifferenceWithoutRound, 2);
            final float[] expDifferenceOfRound = {expDifferenceWithoutRound - expDifferenceWithRound};
            String[] expMaxAmountList = idAndAmountAndMaxChildsMap.get(expConsumptionEntity.getNodeId()).getValue();
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
        final Map<String, Float> distributedValue = new HashMap<>();
        idAndEntityMap.forEach((nodeId, entity) -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            String[] expOut = {""};
            if (!idIsLeafMap.get(nodeId)){
                float expDifference = (expConsumptionEntity.getConsumedVolume() + expConsumptionEntity.getDistributedVolume())
                        - idAndAmountAndMaxChildsMap.get(expConsumptionEntity.getNodeId()).getKey();
                float[] amountOnlyPositiveValues = {0f};
                idAndChildListMap.get(expConsumptionEntity.getNodeId()).forEach(childNodeId -> {
                    ConsumptionEntity childConsumptionEntity = (ConsumptionEntity) idAndEntityMap.get(childNodeId);
                    if (childConsumptionEntity.isConnectionFlag())
                        amountOnlyPositiveValues[0] += childConsumptionEntity.getConsumedVolume();
                });
                idAndChildListMap.get(expConsumptionEntity.getNodeId()).forEach(childNodeId -> {
                    ConsumptionEntity childConsumptionEntity = (ConsumptionEntity) idAndEntityMap.get(childNodeId);
                    if (childConsumptionEntity.isConnectionFlag()){
                        distributedValue.put(childNodeId, expDifference * (childConsumptionEntity.getConsumedVolume()
                                / amountOnlyPositiveValues[0]));
                        expOut[0] += ("Id: " + childNodeId + "\n distributedVolume: " + distributedValue.get(childNodeId) + "\n");
                    }
                });
                System.out.println(expOut[0]);
                System.out.println("\\================ VS ===================\\");
            }
            ConsumptionNode actConsumptionNode = super.getNodeOnId(NodeId.nodeIdFromString(expConsumptionEntity.getNodeId()));
            String[] actOut = {""};
            if (!super.isLeaf(actConsumptionNode)){
                super.consumptionDifferencesDistribution(actConsumptionNode);
                actConsumptionNode.getChildNodes().forEach(childNode -> {
                    if (childNode.isConnectionFlag())
                        actOut[0] += "Id: " + childNode.getId() + "\n distributedVolume: " + childNode.getDistributedVolume() + "\n";
                });
            }
            System.out.println(actOut[0]);
            assertEquals(expOut[0], actOut[0]);
        });
    }

    @Test
    public void testGetAllLeafNode() {
        System.out.println("\\======== testGetAllLeafNode() =======\\");
        if (super.accountingNodesTree.isEmpty())
            super.createTreeOnRoot();

        String[] actResult = {super.getAllLeafNode()};
        String[] expResult = {""};
        idAndEntityMap.forEach((nodeId, entity) -> {
            ConsumptionEntity expConsumptionEntity = (ConsumptionEntity) entity;
            if (idIsLeafMap.get(expConsumptionEntity.getNodeId())){
                String addToResult = "\nid: " + expConsumptionEntity.getNodeId() + "\n" +
                        "distributedVolume: " + expConsumptionEntity.getDistributedVolume() + "\n";
                assertTrue(actResult[0].contains(addToResult));
                expResult[0] += (addToResult);
            }
        });
        System.out.println(expResult[0]);
        System.out.println("\\================ VS ===================\\");
        System.out.println(actResult[0]);
    }
}