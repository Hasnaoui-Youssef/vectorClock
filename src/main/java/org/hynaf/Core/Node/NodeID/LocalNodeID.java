package org.hynaf.Core.Node.NodeID;

import java.io.Serial;

public class LocalNodeID extends AbstractNodeID {

    @Serial
    private static final long serialVersionUID = 1L;

    protected LocalNodeID(String nodeID){
        super(nodeID);
    }

    @Override
    public String getID(){
        return "local://" + this.nodeID;
    }
    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.LOCAL;
    }

}
