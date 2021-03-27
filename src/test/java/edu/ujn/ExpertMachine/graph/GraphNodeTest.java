package edu.ujn.ExpertMachine.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphNodeTest {
    @Test
    void testGraphNode() {
        GraphNode node1 = new GraphNode();
        GraphNode node = new GraphNode("lzzz", null);
        GraphNode node2 = new GraphNode("lzzz", null);
        System.out.println(node.equals(node2));
    }
}