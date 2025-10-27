package org.hynaf.Core.Buffers;

import org.hynaf.Core.Messages.Message;
import org.hynaf.Core.ProcessNode;
import org.hynaf.Core.Task.HandleReceivedMessage;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ReceivingBuffer implements Runnable {
    private final ProcessNode hostProcess;
    private final Queue<Message> queue;
    private AtomicBoolean running;
    private final Thread runningThread;

    public ReceivingBuffer(ProcessNode hostNode) {
        this.hostProcess = hostNode;
        this.queue = new ConcurrentLinkedQueue<>();
        this.runningThread = new Thread(this);
        this.running = new AtomicBoolean(true);
    }

    public void receiveMessage(Message message) {
        queue.offer(message);
    }

    public void start() {
        runningThread.setDaemon(true);
        runningThread.start();
    }
    public synchronized void stop() {
       this.running.set(false);
       runningThread.interrupt();
    }

    @Override
    public void run() {
        while(running.get()) {
           Message message = queue.poll();
           if(message == null) continue;
           this.hostProcess.receiveMessage(message);
        }
    }

    public String getHostProcessID() {
        return this.hostProcess.getPid();
    }

}
