package org.hynaf.Core.Buffers;

import org.hynaf.Core.MessageChannel.MessageChannel;
import org.hynaf.Core.Messages.Message;
import org.hynaf.Core.ProcessNode;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SendingBuffer implements Runnable{
    private final ProcessNode hostProcess;
    private final BlockingQueue<Message> queue;
    private final ConcurrentHashMap<String, MessageChannel> channels;
    private final AtomicBoolean running;
    private final Thread runningThread;
    public SendingBuffer(ProcessNode process) {
        this.hostProcess = process;
        this.queue = new LinkedBlockingQueue<>();
        this.channels = new ConcurrentHashMap<>();
        this.running = new AtomicBoolean(true);
        this.runningThread = new Thread(this);
    }

    public void addChannel(String processName, MessageChannel channel) {
        this.channels.put(processName, channel);
    }

    public void putMessage(Message message) {
        queue.offer(message);
    }

    public void stop(){
        this.running.set(false);
        this.runningThread.interrupt();
    }

    public void start(){
        runningThread.setDaemon(true);
        runningThread.start();
    }

    @Override
    public synchronized void run() {
        while(running.get()){
            try{
                Message m = queue.poll(100, TimeUnit.MILLISECONDS);
                if(m == null) continue;
                MessageChannel mc = channels.get(m.getDestination());
                if (mc == null) continue;
                mc.sendMessage(m);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("SendingBuffer[P%s, size=%d]",
                hostProcess.getPid(), queue.size());
    }

}
