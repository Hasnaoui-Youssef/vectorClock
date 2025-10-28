package org.hynaf.Core.Node.NodeID;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractNodeID implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected final String nodeID;
    protected AbstractNodeID(String nodeID) {
        if(nodeID == null || nodeID.trim().isEmpty()) {
            throw new IllegalArgumentException("Node Identifier cannot be null or empty");
        }
        if(!nodeID.matches("^[a-fA-F][0-9a-fA-F_-]+$")) {
            throw new IllegalArgumentException("Node Identifier must start with an alphabetic character"
                    + "and only contain alphanumeric characters, underscores or hyphens : "
                    + nodeID);
        }
        this.nodeID = nodeID;
    }
    public enum ConnectionType {
        LOCAL,
        PROCESS,
        NETWORK,
        REMOTE
    }
    public abstract String getID();
    public abstract ConnectionType getConnectionType();
    public final String getIdentifier() {
        return nodeID;
    }

    @Override
    public final boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof AbstractNodeID other)) return false;
        return this.getID().equals(other.getID());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public final String toString() {
        return getID();
    }

}
