package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AsCastService {

    private final INode node;
    private final HttpService httpService;

    public void receiveAdd(ISource sourceAdd) {

        // Detect inconsistency and delete inconsistent message
        // TODO : Understand isStale function
        if ((this.node.getSource() != null && this.node.getVersion().isStale())
                || (!this.detectLoop(sourceAdd) && (this.node.getSource() != null && this.node.getSource().getDistance() > sourceAdd.getDistance()))) {
            // Resolve inconsistency and propagate fix
            this.receiveDel(sourceAdd);
        } else if (!this.detectLoop(sourceAdd)  && (this.node.getSource() == null || this.node.getSource().getDistance() > sourceAdd.getDistance())) {
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
        if (this.detectEqualSources(sourceDel, this.node.getSource()) && !this.detectLoop(sourceDel)) {
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

    boolean detectLoop(ISource source) {
        for (var neighbor : source.getPath()) {
            if (neighbor.getAddress().equals(this.node.getAddress()))
                return true;
        }
        return false;
    }

    boolean detectEqualSources(ISource source1, ISource source2) {
        return Objects.equals(source1.getNeighbor().getAddress(), source2.getNeighbor().getAddress());
    }
}
