package org.hynaf.Core.Messages;

import java.io.Serializable;
import java.util.Map;

public sealed interface Message extends Serializable
    permits AppMessage {
    interface Type {
        public enum MessageType {
            APPLICATION, RICART_AGRAWALA, MARKER, PROBE
        }
        MessageType get();
    }
    String getSource();
    String getDestination();
    Map<String, Integer> getTimeStamp();
    String getMessageId();
}
