package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Source implements Cloneable {
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

    @Override
    public Source clone() {
        try {
            Source clone = (Source) super.clone();
            clone.node = node.clone();
            clone.path = new LinkedList<>(this.path);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
