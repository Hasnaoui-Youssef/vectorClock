package org.hynaf.Core.MessageChannel;

import org.hynaf.Core.Messages.Message;

public interface MessageChannel {
    void sendMessage(Message msg);
}
