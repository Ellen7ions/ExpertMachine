package edu.ujn.ExpertMachine.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RuleGraphTest {

    RuleGraph graph = new RuleGraph();

    @Test
    void test() {
        GraphNode node1 = new GraphNode("a1", null);
        GraphNode node2 = new GraphNode("a2", node1);
        GraphNode node3 = new GraphNode("a3", node1);
        GraphNode node4 = new GraphNode("a4", node2);
        GraphNode node5 = new GraphNode("a5", node2);

        Collection<GraphNode> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);


        graph.addNodes(nodes);
//        graph.showGraph();

        Collection<GraphNode> actNodes = new ArrayList<>();

        actNodes.add(node4);
//        actNodes.add(node3);
        actNodes.add(node5);

        System.out.println(graph.inference(actNodes));

        System.out.println(graph.toJson());

    }

}