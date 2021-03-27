package edu.ujn.ExpertMachine.utils;

import com.alibaba.fastjson.JSONArray;
import edu.ujn.ExpertMachine.graph.GraphNode;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ParseUtils {

    public static JSONArray getJSONArray(ServletContext servletContext) throws IOException {
        InputStream resourceAsStream = servletContext.getResourceAsStream("/WEB-INF/database2.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

        StringBuilder buffer = new StringBuilder();
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            buffer.append(temp);
        }

        return JSONArray.parseArray(buffer.toString());
    }

    public static Collection<GraphNode> getAllNodes(JSONArray array) {
        Set<GraphNode> nodes = new HashSet<>();

        for (Object object : array) {
            Map<String, Object> map = (Map<String, Object>) object;
            String result = (String) map.get("result");
            JSONArray causes = (JSONArray) map.get("cause");

            GraphNode node = new GraphNode(result, null);
            nodes.add(node);

            for (Object cause : causes) {
                GraphNode cNode = new GraphNode((String) cause, null);
                nodes.add(cNode);
            }
        }
        return nodes;
    }

    public static GraphNode getNode(Collection<GraphNode> nodes, String s) {
        for (GraphNode node : nodes)
            if (node.getProposition().equals(s))
                return node;
        return null;
    }

    public static void linkNodes(JSONArray array, Collection<GraphNode> nodes) {
        for (Object object : array) {
            Map<String, Object> map = (Map<String, Object>) object;
            String result = (String) map.get("result");

            GraphNode resultNode = getNode(nodes, result);

            JSONArray causes = (JSONArray) map.get("cause");

            for (Object cause : causes) {
                GraphNode causeNode = getNode(nodes, (String) cause);
                if (causeNode == null) continue;
                causeNode.setNextNode(resultNode);
            }
        }
    }

}
