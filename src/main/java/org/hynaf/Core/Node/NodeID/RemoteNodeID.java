package org.hynaf.Core.Node.NodeID;

import java.io.Serial;

public class RemoteNodeID extends AbstractNodeID{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String endpoint;
    private final String protocol;

    protected RemoteNodeID(String nodeId, String endpoint, String protocol){
        super(nodeId);
        if(endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException("Node Identifier cannot be null or empty");
        }
        if(!endpoint.matches("^[a-fA-F0-9][0-9a-fA-F_-]+$")) {
            throw new IllegalArgumentException("Endpoint must start with an alphanumeric character"
                    + "and only contain alphanumeric characters, underscores or hyphens : "
                    + endpoint);
        }
        this.endpoint = endpoint;
        if(protocol == null || protocol.trim().isEmpty()) {
            throw new IllegalArgumentException("Node Identifier cannot be null or empty");
        }
        if(!protocol.matches("^[a-fA-F][0-9a-fA-F]+$")) {
            throw new IllegalArgumentException("Protocol must start with an alphabetic character"
                    + "and only contain alphanumeric characters: "
                    + protocol);
        }
        this.protocol = protocol;
    }

    @Override
    public String getID() {
        return protocol + "://" + endpoint + "/" + nodeID;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.REMOTE;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getProtocol() {
        return protocol;
    }
}
