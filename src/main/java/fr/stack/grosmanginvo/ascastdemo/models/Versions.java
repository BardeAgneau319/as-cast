package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class Versions {

    @Builder.Default
    private Map<String, Integer> lastVersions = new HashMap<>();

    public boolean isStale(Source incomingSource) {
        return lastVersions.containsKey(incomingSource.getNode().getAddress())
                && lastVersions.get(incomingSource.getNode().getAddress()) > incomingSource.getVersion();
    }

    public void update(Source incomingSource) {
        lastVersions.put(incomingSource.getNode().getAddress(), incomingSource.getVersion());
    }
}
