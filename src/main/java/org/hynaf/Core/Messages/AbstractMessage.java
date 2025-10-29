package org.hynaf.Core.Messages;

import org.hynaf.Core.VectorClock;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/*
The elements within this class are meant as wrappers around the actual implementation specific to the system in work
The implementing classes should then describe the content of the message within the payload
 */
public class AbstractMessage<T> implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    protected final String sourceId;
    protected final String targetId;
    protected final VectorClock timeStamp;
    protected final String messageId;
    protected final T payload;
    protected static final AtomicLong messageCounter = new AtomicLong(0);

    protected AbstractMessage(String sourceId, String targetId, VectorClock vectorClock, T payload) {
       this.sourceId = sourceId;
       this.targetId = targetId;
       this.timeStamp = vectorClock.copy();
       this.messageId = generateMessageId(sourceId, targetId);
       this.payload = payload;
    }

    private String generateMessageId(String sourceId, String targetId) {
       long counter = messageCounter.getAndIncrement();
       long timeStamp = System.currentTimeMillis();
       return String.format("m_%s_%s_%d_%d",
               sourceId.replace("://", "_").replace("/", "_").replace(":", "_"),
               targetId.replace("://", "_").replace("/", "_").replace(":", "_"),
               counter,
               timeStamp
       );
    }

    public final String getSourceId() {
        return this.sourceId;
    }
    public final String getTargetId() {
        return this.targetId;
    }

}
