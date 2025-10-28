package org.hynaf.Core.Node.NodeID;

import java.io.Serial;

public class ProcessNodeID extends AbstractNodeID {

    @Serial
    private static final long serialVersionUID = 1L;
    private final int port;

    protected ProcessNodeID(String nodeID, int port) {
        super(nodeID);
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port: " + port + " (must be between 0 and 65535)");
        }
        this.port = port;
    }

    @Override
    public String getID(){
        return "process://localhost:" + port + "/" + nodeID;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.PROCESS;
    }

    public int getPort() {
        return port;
    }

}
