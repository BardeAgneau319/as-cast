package fr.stack.grosmanginvo.ascastdemo.models;

import java.util.List;

public interface IServer {

    public void setSource(ISource source);

    public ISource getSource();

    public List<INode> getNeighbors();

    public void setNeighbors(List<INode> neighbors);

    public boolean isSource();

    public void setIsSource(boolean isSource);

    public String getAddress();

    boolean isStale(ISource incomingSource);

    void updateVersion(ISource incomingSource);

    public INode toNode();

}
