package org.hynaf.Core;

import org.hynaf.Core.Messages.Message;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBuffer {
    private final BlockingQueue<Message> messageQueue;
    public MessageBuffer() {
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public MessageBuffer(int capacity) {
        this.messageQueue = new LinkedBlockingQueue<>(capacity);
    }
    public boolean put(Message message) {
        return this.messageQueue.offer(message);
    }
    public Message take() throws InterruptedException{
        return messageQueue.take();
    }
    public Message poll() {
        return messageQueue.poll();
    }
    public Message peek() {
        return messageQueue.peek();
    }

    public boolean isEmpty() {
        return messageQueue.isEmpty();
    }
    public int size() {
        return messageQueue.size();
    }
}
