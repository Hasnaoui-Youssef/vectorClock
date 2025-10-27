package org.hynaf.Core.Messages;

import org.hynaf.Core.ProcessNode;

public class AppMessagePayload {
    final private Object payload;
    final private ProcessNode targetNode;
    final private ProcessNode hostNode;
    public AppMessagePayload(Object payload, ProcessNode targetNode, ProcessNode hostNode) {
        this.payload = payload;
        this.targetNode = targetNode;
        this.hostNode = hostNode;
    }
    public Object getPayload() {
        return payload;
    }
    public ProcessNode getTargetNode() {
        return targetNode;
    }
    public ProcessNode getHostNode() {
        return hostNode;
    }
    public void sendMsg(){
        this.getHostNode().sendMessage(this.getPayload(), this.getTargetNode());
    }
}
