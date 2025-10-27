package org.hynaf.Core;

import org.hynaf.Core.Buffers.ReceivingBuffer;
import org.hynaf.Core.Buffers.SendingBuffer;
import org.hynaf.Core.Task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProcessNode {
    final private int pid;
    final private Map<Integer, ReceivingBuffer> receivingBuffers;
    final private Map<Integer, SendingBuffer>  sendingBuffers;
    final private Map<Integer, MessageChannel>  messageChannels;
    final private ConcurrentLinkedQueue<Task> taskQueue;
    public ProcessNode(int pid) {
        this.pid = pid;
        this.receivingBuffers = new ConcurrentHashMap<>();
        this.sendingBuffers = new ConcurrentHashMap<>();
        this.messageChannels = new ConcurrentHashMap<>();
        this.taskQueue = new ConcurrentLinkedQueue<>();
    }
    public int getPid() {
        return pid;
    }

    public synchronized void enqueueTask(Task task){
        this.taskQueue.add(task);
    }
}
