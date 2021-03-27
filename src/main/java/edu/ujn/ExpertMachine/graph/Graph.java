package edu.ujn.ExpertMachine.graph;

import edu.ujn.ExpertMachine.utils.Json;

import java.util.ArrayList;
import java.util.Collection;

public interface Graph extends Json {
    void addNode(GraphNode node);

    void showGraph();

    void loadJSON();

//    void topForward();

//    void activateInputNode(ArrayList<GraphNode> nodes);
}
