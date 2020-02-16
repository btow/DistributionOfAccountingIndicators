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
                curNumberParentOfNode = 2, curNumberChildOfNode = 1, childOfNodeCounter = 0,
                nodeCounter = 0, level = 0;
        for (int i = 0; i < numberLevels; i++) {
            List<String> levelParentNodeIds = new ArrayList<>();
        }
        while (level < numberLevels) {
            nodeCounter++;
            if (nodeCounter == numberTestCases)
                break;
            ConsumptionEntity testCase = new ConsumptionEntity();
            testCase.setUUID(UUID.randomUUID());
            String nodeId = "";
            float consumedVolume = i;
            for (int j = 0; j < level; j++) {
                nodeId += (j + ".");
                consumedVolume += (j / 10);
            }
            nodeId = nodeId.substring(0, ((level + 1) * 2 - 2));
            testCase.setNodeId(nodeId);
            testCase.setConsumedVolume(consumedVolume);
            testCase.setDistributedVolume(consumedVolume);
            testCase.setConnectionFlag(nodeCounter % 2 == 1);
            if (level < (numberLevels - 1)) {
                parentNodeIds.get(level).add(testCase.getNodeId());
                curNumberChildOfNode++;
            } else {
                if (childOfNodeCounter == curNumberChildOfNode) {
                    curNumberParentOfNode++;
                    if (curNumberParentOfNode == parentNodeIds.get(level - 1).size()) {
                        throw new RuntimeException("The list of " + (level - 1) + "-level nodes ended unexpectedly.");
                    } else if (curNumberParentOfNode == maxNumberChildOfNode) {
                        curNumberParentOfNode = 2;
                    }
                    childOfNodeCounter = -1;
                }
                testCase.setParentNode(parentNodeIds.get(level - 1).get(curNumberParentOfNode));
                childOfNodeCounter++;
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