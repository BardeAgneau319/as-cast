package fr.stack.grosmanginvo.ascastdemo.models;

import java.util.List;

public class Node implements INode {

    private ISource source;
    private List<?> neighbors;
    private boolean isSource;

    @Override
    public void setSource(ISource source) {
        this.source = source;
    }

    @Override
    public ISource getSource() {
        return this.source;
    }

    @Override
    public List<?> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public void setNeighbors(List<?> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean isSource() {
        return isSource;
    }

    @Override
    public void setIsSource(boolean isSource) {
        this.isSource = isSource;
    }
}
