package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Node implements INode {

    private ISource source;
    @Builder.Default
    private List<INeighbor> neighbors = new ArrayList<>();
    private boolean isSource;
    private String address;
    private IVersion version;

    @Override
    public void setSource(ISource source) {
        this.source = source;
    }

    @Override
    public ISource getSource() {
        return this.source;
    }

    @Override
    public List<INeighbor> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public void setNeighbors(List<INeighbor> neighbors) {
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

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public IVersion getVersion() {
        return version;
    }
}
