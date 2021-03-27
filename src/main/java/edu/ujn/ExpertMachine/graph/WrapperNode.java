package edu.ujn.ExpertMachine.graph;

import com.alibaba.fastjson.annotation.JSONField;

public class WrapperNode {
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "next")
    private int next;
    @JSONField(name = "activity")
    private boolean activity;
    @JSONField(name = "knowledge")
    private String knowledge;

    public WrapperNode(int level, int id, int next, boolean activity, String knowledge) {
        this.level = level;
        this.id = id;
        this.next = next;
        this.activity = activity;
        this.knowledge = knowledge;
    }

    public WrapperNode(GraphNode node) {
        this.level = node.getLevel();
        this.id = node.getID();
        this.next = node.getNextNode() == null ? -1 : node.getNextNode().getID();
        this.activity = node.checkIfActivity();
        this.knowledge = node.getProposition();
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WrapperNode that = (WrapperNode) o;

        if (level != that.level) return false;
        if (id != that.id) return false;
        if (next != that.next) return false;
        return activity == that.activity;
    }

    @Override
    public int hashCode() {
        int result = level;
        result = 31 * result + id;
        result = 31 * result + next;
        result = 31 * result + (activity ? 1 : 0);
        return result;
    }
}
