package org.hynaf.Core.Node.NodeID;

import java.io.Serial;

public class NetworkNodeID extends AbstractNodeID{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String host;
    private final int port;

    protected NetworkNodeID(String nodeId, String host, int port) {
        super(nodeId);

        if(host == null || host.trim().isEmpty()) {
            throw new IllegalArgumentException("Host cannot be null or empty");
        }
        if(!host.matches("^[0-9a-fA-F_-]+$")) {
            throw new IllegalArgumentException("Host must only contain alphanumeric characters, underscores or hyphens : "
                    + host);
        }
        this.host = host;
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port: " + port + " (must be between 0 and 65535)");
        }
        this.port = port;
    }

    @Override
    public String getID(){
        return "network://" + host + ":" + port + "/" + nodeID;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.NETWORK;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
