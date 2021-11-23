package fr.stack.grosmanginvo.ascastdemo;

import lombok.SneakyThrows;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.*;

public class GraphApplication {
    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Minimal Graph");

        /* Graph creation
        addNode(graph, "A", true);
        addNode(graph, "B", false);
        addNode(graph, "C", false);
        addNode(graph, "D", true);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BD", "B", "D");
        graph.addEdge("CD", "C", "D");

        FileSinkDGS fs = new FileSinkDGS();
        fs.writeAll(graph, "minimal.dgs");
        */

        FileSourceDGS fs = new FileSourceDGS();
        fs.addSink(graph);
        fs.readAll("minimal.dgs");
        graph.display();
    }

    private static void addNode(Graph graph, String key, Boolean isSource) {
        Node n = graph.addNode(key);
        n.setAttribute("isSource", isSource);

        // styling
        n.setAttribute("ui.label", key);
        if (isSource) n.setAttribute("ui.style", "shape:circle;fill-color: red;size: 30px;");
        else n.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
    }
}
