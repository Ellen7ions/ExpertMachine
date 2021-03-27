package edu.ujn.ExpertMachine.graph;

import edu.ujn.ExpertMachine.knowledge.Proposition;

import java.util.Objects;

public class GraphNode implements Proposition {
    private static Integer nodeAccId = 0;

    private Integer id;

    // @Deprecated
    private Boolean alive;

    // calculate while init Nodes
    private Integer level;

    // calculate while add Node
    private Integer ind;
    private Integer oud;

    // calculate after topForward
    private Integer threshold;
    private Integer activity;

    // init
    private String proposition;
    private GraphNode nextNode;

    private void setId() {
        GraphNode.nodeAccId += 1;
        this.id = GraphNode.nodeAccId;
    }

    public GraphNode() {
        this.setId();
        this.alive = Boolean.FALSE;
        this.level = this.ind = this.oud = this.threshold = this.activity = 0;
        this.proposition = null;
        this.nextNode = null;
    }

    public GraphNode(String proposition, GraphNode nextNode) {
        this();
        this.proposition = proposition;
        this.nextNode = nextNode;
    }

    public boolean checkNotActivityNotDead() {
        return this.activity > 0 && this.activity < this.threshold;
    }

    public boolean checkIfActivity() {
        return this.activity >= this.threshold;
    }

    public void decInd() {
        this.ind -= 1;
    }

    public void decOud() {
        this.oud -= 1;
    }

    public void incInd() {
        this.ind += 1;
    }

    public void incOud() {
        this.oud += 1;
    }

    public void incActivity() {
        this.activity += 1;
    }

    public int getID() {
        return this.id;
    };

    public GraphNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(GraphNode nextNode) {
        this.nextNode = nextNode;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getInd() {
        return ind;
    }

    public void setInd(Integer ind) {
        this.ind = ind;
    }

    public Integer getOud() {
        return oud;
    }

    public void setOud(Integer oud) {
        this.oud = oud;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    @Override
    public String getProposition() {
        return this.proposition;
    }

    @Override
    public void setProposition(String s) {
        this.proposition = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphNode node = (GraphNode) o;

        return Objects.equals(proposition, node.proposition);
    }

    @Override
    public int hashCode() {
        return proposition != null ? proposition.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GraphNode{" + "" +
                "id=" + id +
                ", level=" + level +
                ", ind=" + ind +
                ", oud=" + oud +
                ", threshold=" + threshold +
                ", activity=" + activity +
                ", proposition='" + proposition + '\'' +
                ", nextNode=" + (nextNode == null ? "null" : nextNode.getProposition()) +
                '}';
    }
}
