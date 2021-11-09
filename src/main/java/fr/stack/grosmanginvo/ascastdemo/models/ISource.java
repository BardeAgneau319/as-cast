package fr.stack.grosmanginvo.ascastdemo.models;

import java.util.List;

public interface ISource {

    INeighbor getNeighbor();
    void setNeighbor(INeighbor neighbor);

    int getDistance();
    void setDistance(int distance);

    List<INeighbor> getPath();
    void setPath(List<INeighbor> neighbors);
}
