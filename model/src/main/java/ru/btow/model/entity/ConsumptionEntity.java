package ru.btow.model.entity;

import ru.btow.model.dao.EntityInterface;

import java.util.List;
import java.util.UUID;

/**
 * Created by btow on 16.02.2020.
 */
public class ConsumptionEntity implements EntityInterface {

    private String uid;
    private String nodeId;
    private float consumedVolume;
    private float distributedVolume;
    private boolean connectionFlag;
    private String parentNode;

    @Override
    public UUID getUUID() {
        return UUID.fromString(uid);
    }

    @Override
    public void setUUID(UUID uuid) {
       this.uid = uuid.toString();
    }

    @Override
    public void updateEntity(EntityInterface entity) {
        ConsumptionEntity consumptionEntity = (ConsumptionEntity) entity;
        this.nodeId = consumptionEntity.nodeId;
        this.consumedVolume = consumptionEntity.consumedVolume;
        this.distributedVolume = consumptionEntity.distributedVolume;
        this.connectionFlag = consumptionEntity.connectionFlag;
        this.parentNode = consumptionEntity.parentNode;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public float getConsumedVolume() {
        return consumedVolume;
    }

    public void setConsumedVolume(float consumedVolume) {
        this.consumedVolume = consumedVolume;
    }

    public float getDistributedVolume() {
        return distributedVolume;
    }

    public void setDistributedVolume(float distributedVolume) {
        this.distributedVolume = distributedVolume;
    }

    public boolean isConnectionFlag() {
        return connectionFlag;
    }

    public void setConnectionFlag(boolean connectionFlag) {
        this.connectionFlag = connectionFlag;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }
}
