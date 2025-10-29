package org.hynaf.Core.Messages;

import org.hynaf.Core.VectorClock;

public abstract class MessageBuilder<T, M extends AbstractMessage<T>, B extends MessageBuilder<T, M, B>> {
    protected String sourceId;
    protected String targetId;
    protected VectorClock timeStamp;
    protected T payload;

    @SuppressWarnings("unchecked")
    protected final B builder() {
        return (B) this;
    }

    public B from(String sourceId) {
        this.sourceId = sourceId;
        return builder();
    }

    public B to(String targetId) {
        this.targetId = targetId;
        return builder();
    }

    public B withClock(VectorClock timeStamp) {
        this.timeStamp = timeStamp.copy();
        return builder();
    }

    public B withPayload(T payload) {
        this.payload = payload;
        return builder();
    }
    public abstract M build();
}
