package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source implements ISource {
    private INeighbor neighbor;
    private int distance;
    private List<INeighbor> path;
    private IVersion version;

    @Override
    public boolean isLooping(INode node) {
        for (var neighbor : path) {
            if (neighbor.getAddress().equals(node.getAddress()))
                return true;
        }
        return false;
    }

    @Override
    public boolean isSame(ISource source) {
        return this.neighbor.getAddress().equals(source.getNeighbor().getAddress());
    }
}
