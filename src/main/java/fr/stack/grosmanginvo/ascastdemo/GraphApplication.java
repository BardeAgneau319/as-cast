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
        FileSourceDGS fs = new FileSourceDGS();
        fs.addSink(graph);
        fs.readAll("minimal.dgs");
        graph.display();
    }
}
