package ru.btow.model.providers;

import org.junit.Before;
import org.junit.Test;
import ru.btow.model.dao.EntityInterface;
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
        ConsumptionEntity testCase1 = newConsumptionEntity(
                UUID.randomUUID(), "1", 1000.0f, 0.0f, true, null);
        this.tastCases.add(testCase1);
        super.add(testCase1);
        ConsumptionEntity testCase2 = newConsumptionEntity(
                UUID.randomUUID(), "1.1", 300.0f, 101.25f, true, testCase1.getUUID());
        this.tastCases.add(testCase2);
        super.add(testCase2);
        ConsumptionEntity testCase3 = newConsumptionEntity(
                UUID.randomUUID(), "1.2", 200.0f, 67.5f, true, testCase1.getUUID());
        this.tastCases.add(testCase3);
        super.add(testCase3);
        ConsumptionEntity testCase4 = newConsumptionEntity(
                UUID.randomUUID(), "1.3", 70.0f, 0.0f, false, testCase1.getUUID());
        this.tastCases.add(testCase4);
        super.add(testCase4);
        ConsumptionEntity testCase5 = newConsumptionEntity(
                UUID.randomUUID(), "1.4", 300.0f, 101.25f, true, testCase1.getUUID());
        this.tastCases.add(testCase5);
        super.add(testCase5);
        ConsumptionEntity testCase6 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.1", 150.0f, 0.0f, true, testCase3.getUUID());
        this.tastCases.add(testCase6);
        super.add(testCase6);
        ConsumptionEntity testCase7 = newConsumptionEntity(
                UUID.randomUUID(), "1.2.2", 50.0f, 0.0f, true, testCase3.getUUID());
        this.tastCases.add(testCase7);
        super.add(testCase7);
        ConsumptionEntity testCase8 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1", 201.0f, 0.0f, true, testCase5.getUUID());
        this.tastCases.add(testCase8);
        super.add(testCase8);
        ConsumptionEntity testCase9 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.2", 33.0f, 0.0f, true, testCase5.getUUID());
        this.tastCases.add(testCase9);
        super.add(testCase9);
        ConsumptionEntity testCase10 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.1", 77.0f, 0.0f, true, testCase8.getUUID());
        this.tastCases.add(testCase10);
        super.add(testCase10);
        ConsumptionEntity testCase11 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.2", 46.0f, 0.0f, true, testCase8.getUUID());
        this.tastCases.add(testCase11);
        super.add(testCase11);
        ConsumptionEntity testCase12 = newConsumptionEntity(
                UUID.randomUUID(), "1.4.1.3", 80.0f, 0.0f, true, testCase8.getUUID());
        this.tastCases.add(testCase12);
        super.add(testCase12);
    }

    @Test
    public void openConnectionTest() {
        System.out.println("\\======== openConnectionTest() =======\\");
        System.out.println(this);
        assertTrue(openConnection(this.args) instanceof DataProvider);
    }

    @Test
    public void getAllEntityTest(){
        System.out.println("\\======== getAllEntityTest() =======\\");
        System.out.println(super.getAllEntity());
    }

    @Test
    public void createAnEntityTest() {
        System.out.println("\\======== createAnEntityTest() =======\\");
        System.out.println("Size = " + tastCases.size());
        System.out.println(tastCases);
        boolean result = super.createAnEntity(newConsumptionEntity(
                UUID.randomUUID(), "1.1.1", 300.0f, 0.0f, true, tastCases.get(1).getUUID()
        ));
        System.out.println("\\================================ VS ==================================\\");
        List<EntityInterface> entityList = super.getAllEntity();
        System.out.println("Size = " + entityList.size());
        System.out.println(entityList);
        assertTrue(result);
    }

    @Test
    public void readTheAnEntityTest() {
        System.out.println("\\======== readTheAnEntityTest() =======\\");
        tastCases.forEach(expEntity -> {
            System.out.println(expEntity);
            System.out.println("\\================================ VS ==================================\\");
            EntityInterface actEntity = super.readTheAnEntity(expEntity.getUUID());
            System.out.println(actEntity);
            System.out.println();
            assertEquals(expEntity, actEntity);
        });
    }

    @Test
    public void updateEntityTest() {
        System.out.println("\\======== updateEntityTest() =======\\");
        tastCases.forEach(expEntity -> {
            ConsumptionEntity updatedEntity = expEntity;
            expEntity.setConsumedVolume(expEntity.getConsumedVolume() * 2);
            System.out.println(expEntity);
            updatedEntity.setConsumedVolume(updatedEntity.getConsumedVolume() * 2);
            super.updateEntity(expEntity.getUUID(), updatedEntity);
            System.out.println("\\================================ VS ==================================\\");
            ConsumptionEntity actEntity = (ConsumptionEntity) super.readTheAnEntity(expEntity.getUUID());
            System.out.println(actEntity);
            System.out.println();
            assertTrue(expEntity.equals(actEntity));
        });
    }

    @Test
    public void deleteTheEntityTest() {
        System.out.println("\\======== deleteTheEntityTest() =======\\");
        System.out.println("Size = " + tastCases.size());
        System.out.println(tastCases);
        int deletedEntity = tastCases.size() - 1;
        boolean result = super.deleteTheEntity(tastCases.get(deletedEntity).getUUID());
        System.out.println("\\================================ VS ==================================\\");
        List<EntityInterface> entityList = super.getAllEntity();
        System.out.println("Size = " + entityList.size());
        System.out.println(entityList);
        assertTrue(result);
    }
}