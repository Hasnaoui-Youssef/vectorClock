package org.hynaf.Core;

import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VectorClock {
    private final Map<String, Integer> clock;
    private final String pid;


    public VectorClock(String pid, int size) {
        this.pid = pid;
        this.clock = new ConcurrentHashMap<>();
        this.clock.put(pid, size);
    }
    public VectorClock(String pid) {
        this.pid = pid;
        this.clock = new ConcurrentHashMap<>();
        this.clock.put(pid, 0);
    }
    public synchronized VectorClock copy(){
        VectorClock copy = new VectorClock(pid);
        copy.clock.putAll(this.clock);
        return copy;
    }

    public synchronized Integer getProcessorTick(String id) {
        return clock.get(id);
    }
    public synchronized int size() {
        return clock.size();
    }
    public String getProcessorID(){
       return this.pid;
    }

    public synchronized void tick() {
        clock.put(pid, clock.get(pid) + 1);
    }
    public synchronized void update(VectorClock other){
        for(Map.Entry<String, Integer> entry : other.clock.entrySet()) {
            clock.put(
                    entry.getKey(),
                    Math.max(clock.getOrDefault(entry.getKey(), 0), entry.getValue())
            );
        }
    }
    public synchronized boolean happenedBefore(VectorClock other) {
        boolean strictlyLess = false;
        for(Map.Entry<String, Integer> entry : this.clock.entrySet()) {
            String process = entry.getKey();
            int timeStamp = entry.getValue();
            int otherTime = other.clock.getOrDefault(process, 0);
            if(timeStamp > otherTime) {
                return false;
            }
            if(timeStamp < otherTime) {
                strictlyLess = true;
            }
        }
        for (Map.Entry<String, Integer> entry : other.clock.entrySet()) {
            String process = entry.getKey();
            if(!clock.containsKey(process) && entry.getValue() > 0){
                strictlyLess = true;
                break;
            }
        }
        return strictlyLess;
    }
    public synchronized boolean concurrentWith(VectorClock other) {
        return !this.happenedBefore(other) && !other.happenedBefore(this);
    }

    public Map<String, Integer> toMap(){
        return new HashMap<>(this.clock);
    }

    public VectorClock(String pid, Map<String, Integer> vclock){
       this.pid = pid;
       this.clock = new ConcurrentHashMap<>(vclock);
    }

    @Override
    public synchronized boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof VectorClock other)) return false;
        return this.clock.equals(other.clock);
    }



    @Override
    public synchronized int hashCode(){
       int clockHash = clock.hashCode();
       String[] hashable = {""+clockHash, pid};
       return Arrays.hashCode(hashable);
    }

    @Override
    public String toString() {
        return "[PID " + this.pid + "] " + clock.toString();
    }
}
