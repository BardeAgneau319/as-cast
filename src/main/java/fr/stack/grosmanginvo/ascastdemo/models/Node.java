package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Node implements Cloneable{
    private String address;

    @Override
    public Node clone() {
        try {
            return (Node) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
