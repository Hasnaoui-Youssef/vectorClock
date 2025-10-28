package org.hynaf.Core.Node;

import org.hynaf.Core.VectorClock;

public abstract class Node {
    private final String id;
    private final VectorClock vectorClock;
    public Node(String id) {
        this.id = id;
        this.vectorClock = new VectorClock(id);
    }

    final public VectorClock getClock(){
        return this.vectorClock;
    }

}
