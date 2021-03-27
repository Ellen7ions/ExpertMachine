package edu.ujn.ExpertMachine.utils;

import com.alibaba.fastjson.JSONArray;
import edu.ujn.ExpertMachine.graph.GraphNode;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ParseUtilsTest {

    @Test
    void getAllNodes() {
        JSONArray jsonArray = JSONArray.parseArray("[\n" +
                "  {\n" +
                "    \"cause\": [\n" +
                "      \"a4\",\n" +
                "      \"a5\"\n" +
                "    ],\n" +
                "    \"result\": \"a2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"cause\": [\n" +
                "      \"a3\",\n" +
                "      \"a2\"\n" +
                "    ],\n" +
                "    \"result\": \"a1\"\n" +
                "  }\n" +
                "]");
        Collection<GraphNode> nodes = ParseUtils.getAllNodes(jsonArray);
        ParseUtils.linkNodes(jsonArray, nodes);
        System.out.println(nodes);

    }
}