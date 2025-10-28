package org.hynaf.Core.Messages;

import org.hynaf.Core.VectorClock;

import java.io.Serial;
import java.io.Serializable;

public abstract class AbstractMessage<T> implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    protected final String sourceId;
    protected final String targetId;
    protected final VectorClock timeStamp;
    protected final String messageId;
    protected final T payload;


    protected AbstractMessage(String sourceId, String targetId, VectorClock vectorClock, T payload) {
       this.sourceId = sourceId;
       this.targetId = targetId;
       this.timeStamp = vectorClock.copy();
       this.messageId = "m_"+sourceId+"_"+targetId;
       this.payload = payload;
    }

}
