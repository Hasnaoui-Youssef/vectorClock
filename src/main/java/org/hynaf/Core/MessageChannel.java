package org.hynaf.Core;

import org.hynaf.Core.Messages.Message;

public abstract class MessageChannel {
    public abstract void start();
    public abstract void stop();
    public abstract void send(Message message);
}
