package fr.stack.grosmanginvo.ascastdemo.models;

import java.util.List;

public interface ISource {

    INode getNode();
    void setNode(INode node);

    int getDistance();
    void setDistance(int distance);

    List<INode> getPath();
    void setPath(List<INode> neighbors);

    int getVersion();

    boolean isBetter(ISource other);

    boolean equals(ISource other);
}
