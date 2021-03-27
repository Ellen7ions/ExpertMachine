package edu.ujn.ExpertMachine.machine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import edu.ujn.ExpertMachine.graph.GraphNode;
import edu.ujn.ExpertMachine.graph.RuleGraph;
import edu.ujn.ExpertMachine.utils.ParseUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class InferenceMachine {
    private final RuleGraph ruleGraph = new RuleGraph();

    private final ServletContext servletContext;

    public InferenceMachine(ServletContext context) throws IOException {
        this.servletContext = context;
        initMachine();
    }

    private ArrayList<GraphNode> loadNodesFromDataBase() throws IOException {
        JSONArray jsonArray = ParseUtils.getJSONArray(this.servletContext);

        ArrayList<GraphNode> allNodes = new ArrayList<>(ParseUtils.getAllNodes(jsonArray));
        ParseUtils.linkNodes(jsonArray, allNodes);

        return allNodes;
    }

    public Collection<GraphNode> loadNodesFromInputJSON(String json) {
        ArrayList<GraphNode> nodes = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(json);

        for (Object object : array) {
            GraphNode node = new GraphNode((String) object, null);
            nodes.add(node);
        }
        return nodes;
    }

    public void initMachine() throws IOException {
        ArrayList<GraphNode> nodes = this.loadNodesFromDataBase();
        this.ruleGraph.addNodes(nodes);
    }

    public void run(String json) {
        this.ruleGraph.inference(loadNodesFromInputJSON(json));
    }

    public String sendGraphJSON() {
        return this.ruleGraph.toJson();
    }


}
