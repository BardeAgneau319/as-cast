package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Server implements IServer {

    private ISource source;
    @Builder.Default
    private List<INode> neighbors = new ArrayList<>();
    private boolean isSource;
    private String address;
    @Builder.Default
    private IVersions versions = new Versions();

    @Override
    public void setSource(ISource source) {
        this.source = source;
    }

    @Override
    public ISource getSource() {
        return this.source;
    }

    @Override
    public List<INode> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public void setNeighbors(List<INode> neighbors) {
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
    public boolean isStale(ISource incomingSource) {
        return versions.isStale(incomingSource);
    }

    @Override
    public void updateVersion(ISource incomingSource) {
        versions.update(incomingSource);
    }

    @Override
    public INode toNode() {
        return new Node(this.address);
    }
}
