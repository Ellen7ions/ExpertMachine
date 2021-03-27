package edu.ujn.ExpertMachine.graph;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;

import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

public class RuleGraph implements Graph {
    private final ArrayList<GraphNode> nodes = new ArrayList<>();

    private final ArrayList<GraphNode> usedActivityNodes = new ArrayList<>();

    private boolean ifHasNode(GraphNode node) {
        if (nodes.isEmpty())
            return false;
        for (GraphNode nd : nodes)
            if (nd.equals(node))
                return true;
        return false;
    }

    /**
     * init Nodes' Level in order to show in the HTML
     */
    public void initNodes() {
        if (nodes.isEmpty()) return;
        Queue<GraphNode> queue = new ArrayDeque<>();

        for (GraphNode nd : nodes)
            if (nd.getInd() == 0) {
                queue.offer(nd);
                nd.setThreshold(1);
            }

        while (!queue.isEmpty()) {
            GraphNode head = queue.poll();
            GraphNode next = head.getNextNode();
            if (next == null) continue;
            next.setLevel(head.getLevel() + 1);
            queue.offer(next);
        }

        for (GraphNode nd : nodes)
            if (nd.getThreshold() == 0)
                nd.setThreshold(nd.getInd());

    }

    /**
     * activate input nodes to prepare inference
     *
     * @param nodes input nodes
     */
    public void addActivityNodes(Collection<GraphNode> nodes) {
        for (GraphNode nd : this.nodes) {
            nd.setActivity(0);
        }

        for (GraphNode nd : nodes) {
            for (GraphNode inNode : this.nodes) {
                if (nd.equals(inNode))
                    inNode.setActivity(1);
            }
        }
    }

    public ArrayList<GraphNode> inference(Collection<GraphNode> activityNodes) {
        this.addActivityNodes(activityNodes);

        this.usedActivityNodes.clear();

        ArrayList<GraphNode> result = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();

        for (GraphNode nd : nodes)
            if (nd.checkIfActivity()) {
                queue.offer(nd);
                this.usedActivityNodes.add(nd);
            }

        while (!queue.isEmpty()) {
            GraphNode head = queue.poll();
            GraphNode next = head.getNextNode();
            if (next == null) continue;
            next.incActivity();
            if (next.checkIfActivity()) {
                queue.offer(next);
                usedActivityNodes.add(next);
            }
        }

        for (GraphNode nd : nodes) {
            GraphNode next = nd.getNextNode();
            if (next == null) {
                if (nd.checkIfActivity())
                    result.add(nd);
                continue;
            }
            if (next.checkNotActivityNotDead() && nd.checkIfActivity())
                result.add(nd);
        }
        return result;
    }

    /**
     * create the graph from the node with a link.
     * these nodes is an edge in fact.
     *
     * @param nodes nodes from the knowledge base
     */
    public void addNodes(Collection<GraphNode> nodes) {
        for (GraphNode node : nodes)
            this.addNode(node);

        initNodes();
    }

    /**
     * see another addNodes(nodes).
     *
     * @param node nodes from the knowledge base
     */
    @Override
    public void addNode(GraphNode node) {
        boolean checkIfHasNode = this.ifHasNode(node);
        if (checkIfHasNode) return;
        if (node.getNextNode() != null) {
            node.incOud();
            node.getNextNode().incInd();
        }

        node.setActivity(0);
        this.nodes.add(node);
    }

    @Override
    public void showGraph() {
        for (GraphNode nd : nodes)
            System.out.println(nd);
    }


    @Override
    public void loadJSON() {
    }

    @Override
    public String toJson() {
        Collection<WrapperNode> wrapperNodes = new ArrayList<>();

        for (GraphNode node : this.nodes) {
            WrapperNode wrapperNode = new WrapperNode(node);
            wrapperNodes.add(wrapperNode);
        }

        return JSON.toJSONString(wrapperNodes);
    }
}
