package org.hynaf.Core.Messages;

import org.hynaf.Core.VectorClock;

import java.util.Map;

public record AppMessage(
        String src,
        String dest,
        String id,
        VectorClock vClock,
        Object payload

) implements Message {

    public AppMessage(String src, String dest,String id, VectorClock vClock, Object payload) {
        this.src = src;
        this.dest = dest;
        this.id = id;
        this.vClock = vClock.copy();
        this.payload = payload;
    }

    @Override
    public String getSource() {
        return src;
    }

    @Override
    public String getDestination() {
        return dest;
    }

    public Map<String, Integer> getTimeStamp(){
        return vClock.toMap();
    }

    public static final Message.Type messageType = () -> Type.MessageType.APPLICATION;

    public String getMessageId() {
        return id;
    }

    public VectorClock vClock() { return vClock; }

    @Override
    public String toString() {
        return "AppMessage{" + "from=" + src + ",to=" + dest + ",vc=" + vClock + ",p=" + payload + '}';
    }
}
