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
        addNode(graph, "A", true);
        addNode(graph, "B", false);
        addNode(graph, "C", false);
        addNode(graph, "D", false);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BD", "B", "D");
        graph.addEdge("CD", "C", "D");
        FileSinkGraphML fs = new FileSinkGraphML();
        fs.writeAll(graph, "perso.xml");
    }

    private static void addNode(Graph graph, String key, Boolean isSource) {
        Node n = graph.addNode(key);
        n.setAttribute("isSource", isSource);

        // styling
        n.setAttribute("ui.label", key);
        n.getAttribute("isSource");
        if (isSource) n.setAttribute("ui.style", "shape:circle;fill-color: red;size: 30px;");
        else n.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
    }
}
