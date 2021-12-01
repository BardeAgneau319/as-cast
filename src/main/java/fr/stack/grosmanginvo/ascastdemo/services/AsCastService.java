package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class AsCastService {

    private final Server server;
    private final HttpService httpService;
    private final Logger logger = Logger.getLogger(AsCastService.class.getName());

    public void receiveAdd(Source sourceAdd) {

        // Detect inconsistency and delete inconsistent message
        // TODO : comprendre deuxième partie condition
        if (this.server.isStale(sourceAdd)) {
            // Resolve inconsistency and propagate fix
            this.logger.log(Level.INFO, "Outdated ADD message received from " + sourceAdd.getNode().getAddress());
            this.receiveDel(sourceAdd);
        } else if (!this.isLooping(sourceAdd) && sourceAdd.isBetter(server.getSource())) {
            // Better source received
            this.logger.log(Level.INFO, "Source updated from ADD to " + sourceAdd.getNode().getAddress());
            this.server.updateVersion(sourceAdd);
            this.server.setSource(sourceAdd);

            Source sourceToSend = computeSourceToSend(sourceAdd);
            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        } // else do nothing
    }

    public void receiveDel(Source sourceDel) {
        // Current source has been deleted
        // TODO : être sûr première partie condition
        if (server.shouldDel(sourceDel) && !this.isLooping(sourceDel)) {
            this.logger.log(Level.INFO, "Source updated from DEL to none");
            this.server.updateVersion(sourceDel);
            this.server.setSource(null);

            Source sourceToSend = computeSourceToSend(sourceDel);
            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastDel(sourceToSend, neighbor);
            }
        } else if (this.server.getSource() != null) {
            // if current source still exists => use it to fill gap for neighbors
            this.logger.log(Level.INFO, "Forwarding current source to neighbors");
            Source sourceToSend = computeSourceToSend(this.server.getSource());
            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        }
    }

    public Optional<Source> forwardSource() {
        if (this.server.getSource() == null) {
            return Optional.empty();
        }

        return Optional.of(computeSourceToSend(this.server.getSource()));
    }

    boolean isLooping(Source source) {
        for (var node : source.getPath()) {
            if (node.getAddress().equals(this.server.getAddress()))
                return true;
        }
        return false;
    }

    Source computeSourceToSend(Source source) {
        List<Node> path = new LinkedList<>(source.getPath());
        path.add(0, this.server.toNode());

        Source sourceToSend = Source.builder()
                .node(source.getNode())
                .distance(source.getDistance() + 1)
                .path(path)
                .version(source.getVersion())
                .build();
        return sourceToSend;
    }

    // current node becoming a source
    public void setAsSource() {
        this.server.setVersion(this.server.getVersion() + 1);
        Source selfSource = Source.builder()
                .node(this.server.toNode())
                .distance(0)
                .version(this.server.getVersion())
                .build();
        this.server.setSource(selfSource);
        this.server.setIsSource(true);

        Source sourceToSend = computeSourceToSend(selfSource);
        for (var neighbor : this.server.getNeighbors()) {
            this.httpService.postAsCastAdd(sourceToSend, neighbor);
        }
    }

    // current node stop being a source
    public void unsetAsSource() {
        if (!this.server.isSource()) {
            this.logger.log(Level.WARNING, "Unable to unset this server as a source since it is already not a source.");
            return;
        }
        this.server.setVersion(this.server.getVersion() + 1);
        Source source = Source.builder()
                .node(this.server.getSource().getNode())
                .distance(this.server.getSource().getDistance())
                .path(this.server.getSource().getPath())
                .version(this.server.getVersion())
                .build();
        this.receiveDel(source);
    }

    /**
     * Adds the node to the list of neighbors and send it the current source if any.
     * @param neighbor The node to add to the list of neighbors
     */
    public void edgeUp(Node neighbor) {
        if (this.server.getNeighbors().contains(neighbor) || neighbor.equals(this.server.toNode())) {
            return;
        }
        this.server.getNeighbors().add(neighbor);
        if (this.getSource() != null) {
            var sourceToSend = this.computeSourceToSend(this.getSource());
            this.httpService.postAsCastAdd(sourceToSend, neighbor);
        }
        this.httpService.postAdminNeighbor(this.server.toNode(), neighbor);
    }

    /**
     * Remove the node from the list of neighbors and spread a delete message if the removed node is the first step
     * on the path to the source.
     * @param neighbor The node to remove from the list of neighbors.
     */
    public void edgeDown(Node neighbor) {
        if (!this.server.getNeighbors().contains(neighbor) || neighbor.equals(this.server.toNode())) {
            return;
        }
        this.server.getNeighbors().remove(neighbor);
        if (this.server.getSource() != null &&
                this.server.getSource().getPath().size() > 0 &&
                Objects.equals(this.server.getSource().getPath().get(0), neighbor)) {
            var sourceToDel = this.server.getSource().clone();
            sourceToDel.setVersion(sourceToDel.getVersion() + 1);
            this.receiveDel(sourceToDel);
        }
        this.httpService.deleteAdminNeighbor(8080 + this.server.getId(), neighbor);
    }

    // simulate fetching data from source
    public MockData getData() {
        if (!this.server.isSource()) {
            Node nextNode = this.server.getSource().getPath().get(0);
            MockData data = this.httpService.getAsCastData(nextNode);
            data.getPath().add(this.server.getAddress());
            return data;
        } else {
            return MockData.builder()
                    .source(this.server.getAddress())
                    .path(List.of(this.server.getAddress()))
                    .text(String.format("This is some data coming from node %s", this.server.getAddress()))
                    .build();
        }
    }

    public boolean isSource() {
        return this.server.isSource();
    }

    public Source getSource() {
        return this.server.getSource();
    }

    @SuppressWarnings("BusyWait")
    public void fetchSourceUntilSuccess() {
        this.logger.log(Level.FINE, "Trying to fetch a source every 10 seconds if no add-event is received.");
        while (this.server.getSource() == null) {
            this.fetchSource();

            if (this.server.getSource() != null) break;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) { }
        }
        logger.log(Level.INFO, String.format("Source fetched : %s", this.server.getSource().getNode().getAddress()));
    }

    private void fetchSource () {
        for (var neighbor : this.server.getNeighbors()) {
            try {
                var source = this.httpService.getAsCastSource(neighbor);
                if (source != null) {
                    this.receiveAdd(source);
                }
            } catch (RestClientException e) {
                logger.log(Level.SEVERE, String.format("Failed to get source from %s", neighbor.getAddress()), e);
            }
        }
    }

    public List<Node> getNeighbors() {
        return this.server.getNeighbors();
    }
}
