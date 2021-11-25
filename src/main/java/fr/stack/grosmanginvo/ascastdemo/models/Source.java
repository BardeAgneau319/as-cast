package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source  {
    private Node node;
    private int distance;
    @Builder.Default
    private List<Node> path = new ArrayList<>();
    private int version;

    public boolean isBetter(Source other) {
        if (other == null) {
            return true;
        }
        return distance < other.getDistance();
    }

    public boolean equals(Source other) {
        return node.getAddress().equals(other.getNode().getAddress());
    }
}
