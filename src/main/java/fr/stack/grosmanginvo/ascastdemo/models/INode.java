package fr.stack.grosmanginvo.ascastdemo.models;

import java.util.List;

public interface INode {

    public void setSource(ISource source);

    public ISource getSource();

    public List<INeighbor> getNeighbors();

    public void setNeighbors(List<INeighbor> neighbors);

    public boolean isSource();

    public void setIsSource(boolean isSource);

    public String getAddress();

    IVersion getVersion();

}
