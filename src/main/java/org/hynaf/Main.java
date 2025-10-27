package org.hynaf;

import org.hynaf.Core.MessageChannel.InMemoryMessageChannel;
import org.hynaf.Core.MessageChannel.MessageChannel;
import org.hynaf.Core.Messages.AppMessage;
import org.hynaf.Core.Messages.AppMessagePayload;
import org.hynaf.Core.ProcessNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int NODE_COUNT = 3;
        final long RUN_TIME_MS = 1_000;
        final Random rand = new Random();

        List<ProcessNode> nodes = new ArrayList<>();
        for (int i = 0; i < NODE_COUNT; i++){
            nodes.add(new ProcessNode("P" + i));
        }
        List<MessageChannel> channels = new ArrayList<>();
        for(int i = 0; i < NODE_COUNT; i++){
            for(int j = i + 1; j < NODE_COUNT; j++){
                MessageChannel mc = new InMemoryMessageChannel(nodes.get(i), nodes.get(j));
                channels.add(mc);
                nodes.get(i).getSendingBuffer().addChannel(nodes.get(j).getPid(), mc);
                nodes.get(j).getSendingBuffer().addChannel(nodes.get(i).getPid(), mc);
            }
        }


        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NODE_COUNT; i++) {
            final int idx = i;
            Thread t = new Thread(() -> {
                ProcessNode self = nodes.get(idx);
                long start = System.currentTimeMillis();

                while (System.currentTimeMillis() - start < RUN_TIME_MS) {
                    try {
                        Thread.sleep(100 + rand.nextInt(50)); // 0.5–1 s delay
                    } catch (InterruptedException ignored) {}

                    boolean send = rand.nextBoolean();

                    if (send) {
                        int targetIdx;
                        do {
                            targetIdx = rand.nextInt(NODE_COUNT);
                        } while (targetIdx == idx);

                        ProcessNode target = nodes.get(targetIdx);
                        String msg = "Message from " + self.getPid() + " to " + target.getPid();
                        AppMessagePayload payload =
                                new AppMessagePayload(msg, target, self);
                        self.execute(payload, AppMessagePayload::sendMsg);
                    } else {
                        self.execute(() -> System.out.println(
                                "Hello from node " + self.getPid()));
                    }
                }
                self.cleanUp();
            }, "Thread-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }

        System.out.println("Simulation complete.");
    }
}