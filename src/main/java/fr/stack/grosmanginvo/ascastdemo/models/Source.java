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
    @Builder.Default
    private List<Node> path = new ArrayList<>();
    private int version;

    public Node getFirstStep() {
        if (this.path.size() == 0) {
            return null;
        }
        return this.getPath().get(0);
    }

    public int getDistance() {
        return this.path.size();
    }

    public boolean isBetter(Source other) {
        if (other == null) {
            return true;
        }
        return getDistance() < other.getDistance();
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
