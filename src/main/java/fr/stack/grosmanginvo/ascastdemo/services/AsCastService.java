package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AsCastService {

    private final Server server;
    private final HttpService httpService;

    public void receiveAdd(Source sourceAdd) {

        // Detect inconsistency and delete inconsistent message
        // TODO : comprendre deuxième partie condition
        if (this.server.isStale(sourceAdd)) {
            // Resolve inconsistency and propagate fix
            this.receiveDel(sourceAdd);
        } else if (!this.isLooping(sourceAdd) && sourceAdd.isBetter(server.getSource())) {
            // Better source received
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
            this.server.updateVersion(sourceDel);
            this.server.setSource(null);

            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastDel(sourceDel, neighbor);
            }
        } else if (this.server.getSource() != null) {
            // if current source still exists => use it to fill gap for neighbors
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
    public void edgeUp() {
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
    public void edgeDown() {
        this.server.setVersion(this.server.getVersion() + 1);
        this.receiveDel(this.server.getSource());
    }

    // simulate fetching data from source
    public MockData getData() {
        if (!this.server.isSource()) {
            Node nextNode = this.server.getSource().getPath().get(0);
            return this.httpService.getAsCastData(nextNode);
        } else {
            return MockData.builder()
                    .address(this.server.getAddress())
                    .someField(String.format("This is some data coming from node %s", this.server.getAddress()))
                    .build();
        }
    }
}
