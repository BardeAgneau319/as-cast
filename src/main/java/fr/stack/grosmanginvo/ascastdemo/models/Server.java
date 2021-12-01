package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Server {

    private int id;
    private Source source;
    @Builder.Default
    private List<Node> neighbors = new ArrayList<>();
    private String address;
    @Builder.Default
    private Versions versions = new Versions();
    @Builder.Default
    private int version = 0;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSource(Source source) {
        this.source = source;
    }
    public Source getSource() {
        return this.source;
    }

    public List<Node> getNeighbors() {
        return this.neighbors;
    }
    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isSource() {
        return this.source != null && this.source.getNode().equals(this.toNode());
    }

    public String getAddress() {
        return address;
    }

    public boolean isStale(Source incomingSource) {
        return versions.isStale(incomingSource);
    }

    public void updateVersion(Source incomingSource) {
        versions.update(incomingSource);
    }

    public boolean shouldDel(Source incomingSource) {
        return this.source != null
                && Objects.equals(this.source.getFirstStep(), incomingSource.getFirstStep())
                && this.source.getVersion() < incomingSource.getVersion();
    }

    public Node toNode() {
        return new Node(this.address);
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public int getVersion() {
        return this.version;
    }
}
