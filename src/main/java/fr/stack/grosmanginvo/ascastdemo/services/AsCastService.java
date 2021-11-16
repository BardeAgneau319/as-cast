package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AsCastService {

    private final IServer server;
    private final HttpService httpService;

    public void receiveAdd(ISource sourceAdd) {

        // Detect inconsistency and delete inconsistent message
        // TODO : deuxième partie condition
        if (this.server.isStale(sourceAdd)) {
            // Resolve inconsistency and propagate fix
            this.receiveDel(sourceAdd);
        } else if (!this.isLooping(sourceAdd) && sourceAdd.isBetter(server.getSource())) {
            // Better source received
            this.server.updateVersion(sourceAdd);
            this.server.setSource(sourceAdd);

            ISource sourceToSend = computeSourceToSend(sourceAdd);
            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        } // else do nothing
    }

    public void receiveDel(ISource sourceDel) {
        // Current source has been deleted
        // TODO : première partie condition
        if (server.getSource().equals(sourceDel) && !this.isLooping(sourceDel)) {
            this.server.updateVersion(sourceDel);
            this.server.setSource(null);

            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastDel(sourceDel, neighbor);
            }
        } else if (this.server.getSource() != null) {
            // if current source still exists => use it to fill gap for neighbors
            ISource sourceToSend = computeSourceToSend(this.server.getSource());
            for (var neighbor : this.server.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        }
    }

    boolean isLooping(ISource source) {
        for (var node : source.getPath()) {
            if (node.getAddress().equals(this.server.getAddress()))
                return true;
        }
        return false;
    }

    ISource computeSourceToSend(ISource source) {
        List<INode> path = source.getPath();
        path.add(this.server.toNode());

        ISource sourceToSend = Source.builder()
                .node(source.getNode())
                .distance(source.getDistance() + 1)
                .path(path)
                .version(source.getVersion())
                .build();
        return sourceToSend;
    }
}
