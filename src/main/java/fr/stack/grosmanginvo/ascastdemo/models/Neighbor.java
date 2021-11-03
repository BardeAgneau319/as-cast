package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Neighbor implements INeighbor {
    private String address;
}
