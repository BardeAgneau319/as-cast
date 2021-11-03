package fr.stack.grosmanginvo.ascastdemo.services;

import fr.stack.grosmanginvo.ascastdemo.models.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AsCastService {

    private final INode node;

    public void receiveAdd(ISource newSource) {
        // CAS 1 : Source plus mauvaise ==> On fait rien

        // CAS 2 : Source périmé ==> conflit & faire un delete TODO
        if (this.node.getSource() != null && this.node.getSource().getDistance() <= newSource.getDistance()) {
            this.receiveDel(this.node.getSource(), newSource);
        } else {
        // CAS 3 : Meilleure source ==> Remplacer envoyer des add
            this.node.setSource(newSource);
            ISource sourceToSend = Source.builder()
                    .neighbor(new Neighbor("myAddress"))
                    .distance(newSource.getDistance() + 1)
                    .build();
            for (var neighbor : this.node.getNeighbors()) {
                this.sendAdd(sourceToSend, neighbor);
            }
        }
    }

    public void sendAdd(ISource newSource, INeighbor receiver) {
        // TODO: CALL HTTP
    }

    private void receiveDel(ISource mySource, ISource newSource) {
    }
}
