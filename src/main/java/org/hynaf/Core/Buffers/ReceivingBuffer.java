package org.hynaf.Core.Buffers;

import org.hynaf.Core.Messages.Message;
import org.hynaf.Core.ProcessNode;
import org.hynaf.Core.Task.HandleReceivedMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ReceivingBuffer {
    private final int hostProcessId;
    private final int targetProcessId;
    private final BlockingQueue<Message> queue;

    private final ProcessNode hostNode;

    public ReceivingBuffer(ProcessNode hostNode, int targetProcessId) {
        this.hostNode = hostNode;
        this.hostProcessId = hostNode.getPid();
        this.targetProcessId = targetProcessId;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void onReceiveMessage(Message message, Consumer<Message> handler) {
        queue.offer(message);
        hostNode.enqueueTask(new HandleReceivedMessage(message, handler));
    }

}
