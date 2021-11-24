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
        var nodeConfig = this.readGraphFile(id);

        this.server = Server.builder()
                .id(id)
                .address(this.computeAddress(id))
                .isSource(nodeConfig.getAttribute("isSource", Boolean.class))
                .build();
        this.initNeighbours(nodeConfig);
    }

    private org.graphstream.graph.Node readGraphFile(int id) throws IOException  {
        Graph graph = new SingleGraph("Minimal Graph");
        FileSourceDGS fs = new FileSourceDGS();
        fs.addSink(graph);
        fs.readAll("perso.dgs");
        return graph.getNode(id);
    }

    private void initNeighbours(org.graphstream.graph.Node nodeConfig) throws IOException {
        List<INode> neighbors = nodeConfig.edges().map(e -> {
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
