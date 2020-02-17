package ru.btow.model.providers;

import org.junit.Before;
import org.junit.Test;
import ru.btow.model.entity.ConsumptionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by btow on 16.02.2020.
 */
public class DataProviderTest extends DataProvider {

    private int numberTestCases = 10;
    private String[] args = {"Any Args"};
    private List<ConsumptionEntity> tastCases = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        List<List<String>> parentNodeIds = new ArrayList<>();
        int numberLevels = 3, maxNumberChildOfNode = (int) Math.floor((numberTestCases - 1) / (numberLevels - 1)),
                curNumberParentOfNodeOnLevel = 1, curNumberChildOfNode = 1,
                parentOfNodeCounterOnLevel = 0, childOfNodeCounter = 0,
                nodeCounter = 0, level = 0;
        for (int i = 0; i < numberLevels; i++) {
            List<String> levelParentNodeIds = new ArrayList<>();
        }
        while (level < numberLevels) {
            nodeCounter++;
            if (nodeCounter == numberTestCases)
                break;
            childOfNodeCounter++;
            if (childOfNodeCounter == curNumberChildOfNode){
                childOfNodeCounter = 1;
                curNumberChildOfNode++;
                if (curNumberChildOfNode == maxNumberChildOfNode){
                    curNumberChildOfNode = 2;

                }
            }
            ConsumptionEntity testCase = new ConsumptionEntity();
            testCase.setUUID(UUID.randomUUID());
            String nodeId = "";
            nodeId = nodeId.substring(0, ((level + 1) * 2 - 2));
            testCase.setNodeId(nodeId);

            float consumedVolume = nodeCounter;
            for (int j = 0; j < (level + 1); j++) {
                nodeId += (j + ".");
                consumedVolume += (j / 10);
            }
            testCase.setConsumedVolume(consumedVolume);
            testCase.setDistributedVolume(consumedVolume);
            testCase.setConnectionFlag(nodeCounter % 2 == 1);
            if (level != 1)
                testCase.setParentNode(parentNodeIds.get(level - 1).get(parentOfNodeCounterOnLevel));
            else {

            }
            this.tastCases.add(testCase);
        }

    }

    @Test
    public void openConnectionTest() {
        System.out.println("\\======== openConnectionTest() =======\\");
        assertTrue(openConnection(this.args) instanceof DataProvider);
    }

    @Test
    public void createAnEntityTest() {
        System.out.println("\\======== createAnEntityTest() =======\\");

    }

    @Test
    public void readTheAnEntityTest() {
        System.out.println("\\======== readTheAnEntityTest() =======\\");
    }

    @Test
    public void updateEntityTest() {
        System.out.println("\\======== updateEntityTest() =======\\");
    }

    @Test
    public void deleteTheEntityTest() {
        System.out.println("\\======== deleteTheEntityTest() =======\\");
    }
}