package fr.stack.grosmanginvo.ascastdemo.models;

public interface IVersions {

    boolean isStale(ISource incomingSource);

    void update(ISource incomingSource);

}
