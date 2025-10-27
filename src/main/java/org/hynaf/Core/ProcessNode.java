package org.hynaf.Core;

import org.hynaf.Core.Buffers.ReceivingBuffer;
import org.hynaf.Core.Buffers.SendingBuffer;
import org.hynaf.Core.Messages.AppMessage;
import org.hynaf.Core.Messages.Message;
import org.hynaf.Core.Task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ProcessNode {
    final private String pid;
    final private VectorClock vectorClock;
    static private int mCount = 0;
    final private ReceivingBuffer receivingBuffer;
    final private SendingBuffer sendingBuffer;

    public ProcessNode(String pid) {
        this.pid = pid;
        this.vectorClock = new VectorClock(pid);
        this.receivingBuffer = new ReceivingBuffer(this);
        this.sendingBuffer = new SendingBuffer(this);
        this.sendingBuffer.start();
        this.receivingBuffer.start();
    }

    public synchronized void receiveMessage(Message msg) {
        vectorClock.tick();
        VectorClock mVectorClock = new VectorClock(msg.getSource(), msg.getTimeStamp());
        System.out.println("[" + pid + "]" + " Received a message, updating vector clock");
        System.out.println("Old clock : " + vectorClock.toString());
        this.vectorClock.update(mVectorClock);
        System.out.println("New clock : " + vectorClock.toString());
    }

    public synchronized void sendMessage(Object payload, ProcessNode other){
        AppMessage msg = new AppMessage(this.pid, other.pid, String.format("%d_%s_%s", mCount++, this.pid, other.pid), this.vectorClock, payload);
        System.out.println("[" + pid + "]" + "->" + "[" + other.pid + "]");
        System.out.println(msg.toString());
        this.sendingBuffer.putMessage(msg);
    }

    public synchronized  <T> void execute(T obj, Consumer<T> handler){
        vectorClock.tick();
        if(obj != null){
            handler.accept(obj);
        }
    }

    public synchronized void execute(Runnable handler){
        vectorClock.tick();
        handler.run();
    }
    public synchronized void cleanUp(){
        System.out.println("[" + pid + "]" + "Clean up");
        this.sendingBuffer.stop();
        this.receivingBuffer.stop();
    }

    public String getPid(){
        return this.pid;
    }

    public ReceivingBuffer getReceivingBuffer(){
        return this.receivingBuffer;
    }
    public SendingBuffer getSendingBuffer(){
        return this.sendingBuffer;
    }
}
