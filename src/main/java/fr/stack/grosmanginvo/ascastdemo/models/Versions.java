package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class Versions implements IVersions {

    @Builder.Default
    private Map<String, Integer> lastVersions = new HashMap<>();

    @Override
    public boolean isStale(ISource incomingSource) {
        return lastVersions.containsKey(incomingSource.getNode().getAddress())
                && lastVersions.get(incomingSource.getNode().getAddress()) > incomingSource.getVersion();
    }

    @Override
    public void update(ISource incomingSource) {
        lastVersions.put(incomingSource.getNode().getAddress(), incomingSource.getVersion());
    }
}
