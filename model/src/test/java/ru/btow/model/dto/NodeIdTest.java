package ru.btow.model.dto;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by btow on 15.02.2020.
 */
public class NodeIdTest {

    private int numberTests;
    private List<Pair<String, List>> expValues = new ArrayList<>();
    private List<NodeId> testCases = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        this.numberTests = 10;
        for (int i = 0; i < numberTests; i++) {
            String expValue = "";
            List<Integer> expList = new ArrayList<>();
            NodeId nodeId = new NodeId();
            for (int j = 0; j <= i; j++) {
                expList.add(j);
                nodeId.add(j);
                expValue += j;
                if (j < i)
                    expValue += ".";
            }
            Pair<String, List> expPairValues = new Pair<>(expValue, expList);
            this.expValues.add(expPairValues);
            this.testCases.add(nodeId);
        }
    }

    @Test
    public void toStringTest() {
        System.out.println("\\=========== toStringTest() ===========\\\n");
        int[] testCounter = {0};
        testCases.forEach(nodeId -> {
            System.out.println(expValues.get(testCounter[0]).getKey());
            System.out.println("============= VS ===========");
            System.out.println(nodeId.toString() + "\n");
            assertEquals(expValues.get(testCounter[0]++).getKey(), nodeId.toString());
        });
    }

    @Test
    public void equalsTest(){
        System.out.println("\\=========== equalsTest() ===========\\\n");
        int[] testCounter = {0};
        testCases.forEach(testCase -> {
            NodeId expNodeId = new NodeId();
            expNodeId.addAll(expValues.get(testCounter[0]).getValue());
            if (testCounter[0]%4 == 1){
                expNodeId.add(testCounter[0]);
                System.out.println("expected value:\n" + expNodeId);
                System.out.println("========= VS ========");
                System.out.println("actual value:\n" + testCases.get(testCounter[0]));
                System.out.println("Are these values equivalent? It is " + expNodeId.equals(testCases.get(testCounter[0])) + "\n");
                assertFalse(expNodeId.equals(testCases.get(testCounter[0]++)));
            } else if (testCounter[0]%4 == 2){
                expNodeId.remove(1);
                System.out.println("expected value:\n" + expNodeId);
                System.out.println("========= VS ========");
                System.out.println("actual value:\n" + testCases.get(testCounter[0]));
                System.out.println("Are these values equivalent? It is " + expNodeId.equals(testCases.get(testCounter[0])) + "\n");
                assertFalse(expNodeId.equals(testCases.get(testCounter[0]++)));
            } else if (testCounter[0]%4 == 3){
                expNodeId.remove(0);
                expNodeId.add(numberTests * 2);
                System.out.println("expected value:\n" + expNodeId);
                System.out.println("========= VS ========");
                System.out.println("actual value:\n" + testCases.get(testCounter[0]));
                System.out.println("Are these values equivalent? It is " + expNodeId.equals(testCases.get(testCounter[0])) + "\n");
                assertFalse(expNodeId.equals(testCases.get(testCounter[0]++)));
            } else {
                System.out.println("expected value:\n" + expNodeId);
                System.out.println("========= VS ========");
                System.out.println("actual value:\n" + testCases.get(testCounter[0]));
                System.out.println("Are these values equivalent? It is " + expNodeId.equals(testCases.get(testCounter[0])) + "\n");
                assertTrue(expNodeId.equals(testCases.get(testCounter[0]++)));
            }
        });
    }

    @Test
    public void nodeIdFromString() {
        System.out.println("\\=========== nodeIdFromString() ===========\\\n");
        expValues.forEach(stringListPair -> {
            System.out.println("expected id: " + stringListPair.getValue());
            System.out.println("========= VS ========");
            NodeId nodeId = NodeId.nodeIdFromString(stringListPair.getKey());
            System.out.println("actual id: " + nodeId);
            assertArrayEquals(stringListPair.getValue().toArray(), nodeId.toArray());
        });
    }
}