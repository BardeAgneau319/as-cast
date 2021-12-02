package fr.stack.grosmanginvo.ascastdemo;

import lombok.SneakyThrows;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkGraphML;

/**
 * Generates a topology file
 */
public class GraphGenerationApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Minimal Graph");

        // Graph du papier de recherche
        /*
        addNode(graph, "A", true);
        addNode(graph, "B", false);
        addNode(graph, "C", false);
        addNode(graph, "D", true);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BD", "B", "D");
        graph.addEdge("CD", "C", "D");
        FileSinkGraphML fs = new FileSinkGraphML();
        fs.writeAll(graph, "minimal.xml");
        */

        // Graph perso avec une seule source
        addNode(graph, "A", "source");
        addNode(graph, "B", "node");
        addNode(graph, "C", "node");
        addNode(graph, "D", "node");

        // relay to add weight
        addNode(graph, "wAB", "relay");
        addNode(graph, "wCD1", "relay");
        addNode(graph, "wCD2", "relay");

        graph.addEdge("AB1", "A", "wAB");
        graph.addEdge("AB2", "wAB", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BD", "B", "D");
        graph.addEdge("CD1", "C", "wCD1");
        graph.addEdge("CD2", "wCD1", "wCD2");
        graph.addEdge("CD3", "wCD2", "D");


        FileSinkGraphML fs = new FileSinkGraphML();
        fs.writeAll(graph, "consistency.xml");
    }

    private static void addNode(Graph graph, String key, String type) {
        Node n = graph.addNode(key);
        n.setAttribute("isSource", type.equals("source"));
        n.setAttribute("isNode", type.equals("source") || type.equals("node"));

        // styling
        n.setAttribute("ui.label", key);
        if (type.equals("source")) n.setAttribute("ui.style", "shape:circle;fill-color: red;size: 30px;");
        else if (type.equals("node")) n.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
        else n.setAttribute("ui.style", "shape:circle;fill-color: grey;size: 5px;");
    }
}
