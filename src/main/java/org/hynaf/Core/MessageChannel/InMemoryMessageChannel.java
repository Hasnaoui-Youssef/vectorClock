package org.hynaf.Core.MessageChannel;

import org.hynaf.Core.Buffers.ReceivingBuffer;
import org.hynaf.Core.Messages.Message;
import org.hynaf.Core.ProcessNode;

public class InMemoryMessageChannel implements MessageChannel {
    private final ReceivingBuffer firstNodeReceivingBuffer;
    private final ReceivingBuffer secondNodeReceivingBuffer;

    public InMemoryMessageChannel(ProcessNode nodeA, ProcessNode nodeB) {
        this.firstNodeReceivingBuffer = nodeA.getReceivingBuffer();
        this.secondNodeReceivingBuffer = nodeB.getReceivingBuffer();
    }

    @Override
    public synchronized void sendMessage(Message msg) {
        if(msg.getDestination().equals(firstNodeReceivingBuffer.getHostProcessID())) {
            firstNodeReceivingBuffer.receiveMessage(msg);
        }else if(msg.getDestination().equals(secondNodeReceivingBuffer.getHostProcessID())) {
            secondNodeReceivingBuffer.receiveMessage(msg);
        }
    }
}
