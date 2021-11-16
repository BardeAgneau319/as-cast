package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source implements ISource {
    private INode node;
    private int distance;
    private List<INode> path;
    private int version;

    @Override
    public boolean isBetter(ISource other) {
        return distance <= other.getDistance();
    }

    @Override
    public boolean equals(ISource other) {
        return node.getAddress().equals(other.getNode().getAddress());
    }
}
