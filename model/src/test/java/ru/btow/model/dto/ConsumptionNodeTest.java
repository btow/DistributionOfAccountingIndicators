package ru.btow.model.dto;

import org.junit.Before;
import org.junit.Test;
import ru.btow.model.dto.ConsumptionNode;
import ru.btow.model.dto.NodeId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by btow on 15.02.2020.
 */
public class ConsumptionNodeTest {

    private int levelCount;
    private int testCount;

    private List<List<Integer>> ids = new ArrayList<>();
    private List<Float> consumedVolumes = new ArrayList<>();
    private List<Float> distributedVolumes = new ArrayList<>();
    private List<Boolean> connectionFlags = new ArrayList<>();
    private List<List<ConsumptionNode>> childNodesList = new ArrayList<>();

    private List<ConsumptionNode> consumptionNodes = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        this.levelCount = 3;
        this.testCount = 10;
        for (int i = 0; i < testCount; i++) {
            List<Integer> id = new ArrayList<>();
            NodeId nodeId = new NodeId();
            Float consumedVolume = Float.valueOf(i);
            for (int j = 0; j < levelCount; j++) {
                id.add(i + j);
                nodeId.add(i + j);
                consumedVolume += j/(10 * (i + 1));
            }
            ids.add(id);
            consumedVolumes.add(consumedVolume);
            distributedVolumes.add(Float.valueOf(i/2));
            connectionFlags.add(i%2 == 0);
            childNodesList.add(new ArrayList<>());

            consumptionNodes.add(new ConsumptionNode(nodeId, consumedVolume, Float.valueOf(i/2), i%2 == 0));
        }
    }

    @Test
    public void getIdTest() {
        System.out.println("\\=========================\\\n\ngetIdTest():");
        for (int i = 0; i < testCount; i++) {
            System.out.print(ids.get(i).toString());
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getId());
            assertEquals(ids.get(i), consumptionNodes.get(i).getId());
        }
    }

    @Test
    public void setIdTest() {
        System.out.println("\\=========================\\\n\nsetIdTest():");
        for (int i = 0; i < testCount; i++) {
            NodeId nodeId = new NodeId();
            for (int j = 0; j < levelCount; j++) {
                nodeId.add(testCount - i);
            }
            consumptionNodes.get(i).setId(nodeId);
            System.out.print(nodeId);
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getId());
            assertEquals(nodeId, consumptionNodes.get(i).getId());
        }
    }

    @Test
    public void getConsumedVolumeTest() {
        System.out.println("\\=========================\\\n\ngetConsumedVolumeTest():");
        for (int i = 0; i < testCount; i++) {
            System.out.print(consumedVolumes.get(i).toString());
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getConsumedVolume());
            float delta = 0F;
            assertEquals(consumedVolumes.get(i), consumptionNodes.get(i).getConsumedVolume(), delta);
        }
    }

    @Test
    public void setConsumedVolumeTest() {
        System.out.println("\\=========================\\\n\nsetConsumedVolumeTest():");
        for (int i = 0; i < testCount; i++) {
            float consumedVolume = (testCount - i)/2;
            consumptionNodes.get(i).setConsumedVolume(consumedVolume);
            System.out.print(consumedVolume);
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getConsumedVolume());
            float delta = 0F;
            assertEquals(consumedVolume, consumptionNodes.get(i).getConsumedVolume(), delta);
        }
    }

    @Test
    public void getDistributedVolumeTest() {
        System.out.println("\\=========================\\\n\ngetDistributedVolumeTest():");
        for (int i = 0; i < testCount; i++) {
            System.out.print(distributedVolumes.get(i).toString());
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getDistributedVolume());
            float delta = 0F;
            assertEquals(distributedVolumes.get(i), consumptionNodes.get(i).getDistributedVolume(), delta);
        }
    }

    @Test
    public void setDistributedVolumeTest() {
        System.out.println("\\=========================\\\n\nsetDistributedVolumeTest():");
        for (int i = 0; i < testCount; i++) {
            float distributedVolume = (testCount - i)/2;
            consumptionNodes.get(i).setDistributedVolume(distributedVolume);
            System.out.print(distributedVolume);
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).getDistributedVolume());
            float delta = 0F;
            assertEquals(distributedVolume, consumptionNodes.get(i).getDistributedVolume(), delta);
        }
    }

    @Test
    public void isConnectionFlagTest() {
        System.out.println("\\=========================\\\n\nisConnectionFlagTest():");
        for (int i = 0; i < testCount; i++) {
            System.out.print(connectionFlags.get(i).toString());
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).isConnectionFlag());
            assertEquals(connectionFlags.get(i), consumptionNodes.get(i).isConnectionFlag());
        }
    }

    @Test
    public void setConnectionFlagTest() {
        System.out.println("\\=========================\\\n\nsetConnectionFlagTest():");
        for (int i = 0; i < testCount; i++) {
            boolean connectionFlag = i%2 == 1;
            consumptionNodes.get(i).setConnectionFlag(connectionFlag);
            System.out.print(connectionFlag);
            System.out.print(" vs ");
            System.out.println(consumptionNodes.get(i).isConnectionFlag());
            assertEquals(connectionFlag, consumptionNodes.get(i).isConnectionFlag());
        }
    }

    @Test
    public void toStringTest() {
        System.out.println("\\=========================\\\n\ntoStringTest():");
        for (int i = 0; i < testCount; i++) {
            final String[] expResult = {"\nid: "};
            final int[] levelCounter = {1};
            ids.get(i).forEach(levelsNumber -> expResult[0] += (levelsNumber.toString() +
                    (levelCounter[0]++ < levelCount ? "." : "")));
            expResult[0] += ("\nconsumedVolume:    " + String.valueOf(consumedVolumes.get(i)));
            expResult[0] += ("\ndistributedVolume: " + String.valueOf(distributedVolumes.get(i)));
            expResult[0] += ("\nconnectionFlag:    " + (connectionFlags.get(i) ? "+" : "-"));
            expResult[0] += ("\nchildNodes:       \n" + (childNodesList.get(i).toString()) + "\n");
            System.out.println("\\========================\\" + expResult[0]);
            System.out.println("\\========== VS ==========\\");
            System.out.println(consumptionNodes.get(i).toString());
            assertEquals(expResult[0], consumptionNodes.get(i).toString());
        }
    }

    @Test
    public void equalsTest() {
        System.out.println("\\=========================\\\n\nequalsTest():");
        for (int i = 0; i < testCount; i++) {
            NodeId nodeId = new NodeId();
            nodeId.addAll(this.ids.get(i));
            ConsumptionNode expConsumptionNode = new ConsumptionNode(
                    nodeId,
                    this.consumedVolumes.get(i),
                    this.distributedVolumes.get(i),
                    this.connectionFlags.get(i)
            );
            System.out.println("expected value:\n" + expConsumptionNode);
            System.out.println("\\========== VS ===========\\");
            System.out.println("actual value:\n" + consumptionNodes.get(i));
            System.out.println("Are these values equivalent? It is " + consumptionNodes.get(i).equals(expConsumptionNode));
            assertTrue(consumptionNodes.get(i).equals(expConsumptionNode));
        }
    }
}