package ru.btow.model.providers;

import com.sun.istack.internal.NotNull;
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

    private String[] args = {"Any Args"};
    private List<ConsumptionEntity> tastCases = new ArrayList<>();

    private ConsumptionEntity newConsumptionEntity(@NotNull UUID uid, @NotNull String id,
                                                   @NotNull float consumedVolume, @NotNull float distributedVolume,
                                                   boolean connectionFlag, String parentNodeUid) {
        ConsumptionEntity result = new ConsumptionEntity();
        result.setUUID(uid);
        result.setNodeId(id);
        result.setConsumedVolume(consumedVolume);
        result.setDistributedVolume(distributedVolume);
        result.setConnectionFlag(connectionFlag);
        result.setParentNode(parentNodeUid);
        return result;
    }

    @Before
    public void setUp() throws Exception {
        ConsumptionEntity testCase1 = newConsumptionEntity(
                UUID.randomUUID(), "1", 1000.0f, 0.0f, true, "");
        this.tastCases.add(testCase1);
        ConsumptionEntity testCase2 = newConsumptionEntity(
                UUID.randomUUID(), "1.1", 300.0f, 101.25f, true, testCase1.getNodeId());
        this.tastCases.add(testCase2);
        ConsumptionEntity testCase3 = newConsumptionEntity(
                UUID.randomUUID(), "1.2", 200.0f, 67.5f, true, testCase1.getNodeId());
        this.tastCases.add(testCase3);
        ConsumptionEntity testCase4 = newConsumptionEntity(
                UUID.randomUUID(), "1.3", 70.0f, 0.0f, false, testCase1.getNodeId());
        this.tastCases.add(testCase4);
        ConsumptionEntity testCase5 = newConsumptionEntity(
                UUID.randomUUID(), "1.4", 300.0f, 101.25f, true, testCase1.getNodeId());
        this.tastCases.add(testCase5);
        ConsumptionEntity testCase6 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.1", 150.0f, 0.0f, true, testCase3.getNodeId());
        this.tastCases.add(testCase6);
        ConsumptionEntity testCase7 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.2", 50.0f, 0.0f, true, testCase3.getNodeId());
        this.tastCases.add(testCase7);
        ConsumptionEntity testCase8 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1", 201.0f, 0.0f, true, testCase5.getNodeId());
        this.tastCases.add(testCase8);
        ConsumptionEntity testCase9 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.2", 33.0f, 0.0f, true, testCase5.getNodeId());
        this.tastCases.add(testCase9);
        ConsumptionEntity testCase10 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.1", 77.0f, 0.0f, true, testCase8.getNodeId());
        this.tastCases.add(testCase10);
        ConsumptionEntity testCase11 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.2", 46.0f, 0.0f, true, testCase8.getNodeId());
        this.tastCases.add(testCase11);
        ConsumptionEntity testCase12 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.3", 80.0f, 0.0f, true, testCase8.getNodeId());
        this.tastCases.add(testCase12);
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