package ru.btow.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by btow on 15.02.2020.
 *
 * Consumption node include a fields:
 * @id - hierarchical node number in the tree structure;
 * @consumedVolume - consumed volume;
 * @distributedVolume - distributed volume;
 * @connectionFlag - connection flag, where "true" is "plus", and "false" is "minus";
 * @childNodes - the list of child's nodes.
 */
public class ConsumptionNode  {

    private NodeId id;
    private float consumedVolume;
    private float distributedVolume;
    private boolean connectionFlag;
    private List<ConsumptionNode> childNodes;

    public ConsumptionNode(NodeId newNodeId){
        this.id = newNodeId;
        this.consumedVolume = 0F;
        this.distributedVolume = 0F;
        this.connectionFlag = true;
        this.childNodes = new ArrayList<>();
    }

    public ConsumptionNode(NodeId id, float consumptionVolume, float distributedVolume
            , boolean connectionFlag) {
        this.id = id;
        this.consumedVolume = consumptionVolume;
        this.distributedVolume = distributedVolume;
        this.connectionFlag = connectionFlag;
        this.childNodes = new ArrayList<>();
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
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

    public List<ConsumptionNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ConsumptionNode> childNodes) {
        this.childNodes = childNodes;
    }

    public void addChildNode(ConsumptionNode node) {
        this.childNodes.add(node);
    }

    @Override
    public String toString() {
        final String[] result = {"\nid: " + id.toString()};
        result[0] += ("\nconsumedVolume:    " + String.valueOf(consumedVolume));
        result[0] += ("\ndistributedVolume: " + String.valueOf(distributedVolume));
        result[0] += ("\nconnectionFlag:    " + (connectionFlag ? "+" : "-"));
        result[0] += ("\nchildNodes:       \n" + childNodes.toString() + "\n");
        return result[0];
    }

    @Override
    public boolean equals(Object node) {
        return this.id.equals(((ConsumptionNode) node).id) &&
                this.connectionFlag==((ConsumptionNode) node).connectionFlag &&
                this.consumedVolume==((ConsumptionNode) node).consumedVolume &&
                this.distributedVolume==((ConsumptionNode) node).distributedVolume &&
                this.childNodes.equals(((ConsumptionNode) node).childNodes);
    }
}
