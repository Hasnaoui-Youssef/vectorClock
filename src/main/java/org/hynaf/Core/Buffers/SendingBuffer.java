package org.hynaf.Core.Buffers;

import org.hynaf.Core.Messages.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SendingBuffer {
    private final int hostProcessId;
    private final int targetProcessId;
    private final BlockingQueue<Message> queue;
    public SendingBuffer(int hostProcessId, int targetProcessId) {
        this.hostProcessId = hostProcessId;
        this.targetProcessId = targetProcessId;
        this.queue = new LinkedBlockingQueue<>();
    }
    public boolean putMessage(Message message) {
        return queue.offer(message);
    }
    public Message getMessage() {
        return queue.poll();
    }
    public boolean hasMessage() {
        return !queue.isEmpty();
    }
    @Override
    public String toString() {
        return String.format("SendingBuffer[P%d→P%d, size=%d]",
                hostProcessId, targetProcessId, queue.size());
    }

}
