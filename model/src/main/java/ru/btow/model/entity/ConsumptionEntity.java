package ru.btow.model.entity;

import ru.btow.model.dao.EntityInterface;

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
    private String parentNodeUid;

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
        this.parentNodeUid = consumptionEntity.parentNodeUid;
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

    public String getParentNodeUid() {
        return parentNodeUid;
    }

    public void setParentNodeUid(String parentNodeUid) {
        this.parentNodeUid = parentNodeUid;
    }

    @Override
    public String toString() {
        return "ConsumptionEntity{" +
                "uid='" + uid + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", consumedVolume=" + consumedVolume +
                ", distributedVolume=" + distributedVolume +
                ", connectionFlag=" + connectionFlag +
                ", parentNode='" + parentNodeUid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsumptionEntity that = (ConsumptionEntity) o;

        if (Float.compare(that.consumedVolume, consumedVolume) != 0) return false;
        if (Float.compare(that.distributedVolume, distributedVolume) != 0) return false;
        if (connectionFlag != that.connectionFlag) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        return parentNodeUid != null ? parentNodeUid.equals(that.parentNodeUid) : that.parentNodeUid == null;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 31 * result + (consumedVolume != +0.0f ? Float.floatToIntBits(consumedVolume) : 0);
        result = 31 * result + (distributedVolume != +0.0f ? Float.floatToIntBits(distributedVolume) : 0);
        result = 31 * result + (connectionFlag ? 1 : 0);
        result = 31 * result + (parentNodeUid != null ? parentNodeUid.hashCode() : 0);
        return result;
    }
}
