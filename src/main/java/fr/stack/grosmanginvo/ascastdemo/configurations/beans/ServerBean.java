package fr.stack.grosmanginvo.ascastdemo.configurations.beans;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.SneakyThrows;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceDGS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Initializes node configuration from environment variables
 */
@Configuration
public class ServerBean {

    private IServer server = null;
    private final Logger logger = Logger.getLogger(ServerBean.class.getName());

    @Autowired
    private Environment env;

    @Bean("server")
    public IServer server() throws IOException {
        if (server ==null) {
            try {
                initServer();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Unable to read topology file. Shutting down server.", e);
                throw e;
            }
        }

        return server;
    }

    private void initServer() throws IOException {
        int id = Integer.parseInt(Objects.requireNonNull(env.getProperty("NODE_ID")));
        this.server = Server.builder()
                .id(id)
                .address(this.computeAddress(id))
                .isSource(env.getProperty("IS_SOURCE", Boolean.TYPE, false))
                .build();
        this.initNeighbours();
    }

    private void initNeighbours() throws IOException {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Minimal Graph");
        FileSourceDGS fs = new FileSourceDGS();
        fs.addSink(graph);
        fs.readAll("minimal.dgs");
        var edges = graph.getNode(server.getId()).edges();

        List<INode> neighbors = edges.map(e -> {
            int id = e.getNode0().getIndex() == this.server.getId() ?
                    e.getNode1().getIndex() :
                    e.getNode0().getIndex();

            return new Node(this.computeAddress(id));
        }).collect(Collectors.toList());

        this.server.setNeighbors(neighbors);
    }

    private String computeAddress(int nodeId) {
        int port = 8080 + nodeId;
        return String.format("http://localhost:%d", port);
    }
}
