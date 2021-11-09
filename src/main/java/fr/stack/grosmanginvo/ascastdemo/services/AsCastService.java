package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AsCastService {

    private final INode node;
    private final HttpService httpService;

    public void receiveAdd(ISource sourceAdd) {

        // Detect inconsistency and delete inconsistent message
        // TODO : Understand isStale function
        if ((this.node.getSource() != null && this.node.getVersion().isStale())
                || (!sourceAdd.isLooping(node) && (this.node.getSource() != null && this.node.getSource().getDistance() > sourceAdd.getDistance()))) {
            // Resolve inconsistency and propagate fix
            this.receiveDel(sourceAdd);
        } else if (!sourceAdd.isLooping(node) && (this.node.getSource() == null || this.node.getSource().getDistance() > sourceAdd.getDistance())) {
            // Better source received
            // TODO : Understand update function
            this.node.getVersion().update();
            this.node.setSource(sourceAdd);

            ISource sourceToSend = Source.builder()
                    .neighbor(new Neighbor(this.node.getAddress()))
                    .distance(sourceAdd.getDistance() + 1)
                    .build();
            for (var neighbor : this.node.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        } // else do nothing
    }

    public void receiveDel(ISource sourceDel) {
        // Current source has been deleted
        if (sourceDel.isSame(this.node.getSource()) && !sourceDel.isLooping(node)) {
            // TODO : Understand update function
            this.node.getVersion().update();
            this.node.setSource(null);

            for (var neighbor : this.node.getNeighbors()) {
                this.httpService.postAsCastDel(sourceDel, neighbor);
            }
        } else if (this.node.getSource() != null) {
            // if current source still exists => use it to fill gap for neighbors
            ISource sourceToSend = Source.builder()
                    .neighbor(new Neighbor(this.node.getAddress()))
                    .distance(this.node.getSource().getDistance() + 1)
                    .build();
            for (var neighbor : this.node.getNeighbors()) {
                this.httpService.postAsCastAdd(sourceToSend, neighbor);
            }
        }
    }
}
