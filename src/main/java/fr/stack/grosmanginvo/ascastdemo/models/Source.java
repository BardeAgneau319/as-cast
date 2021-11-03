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
}
