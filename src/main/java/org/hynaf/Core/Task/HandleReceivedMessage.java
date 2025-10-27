package org.hynaf.Core.Task;

import org.hynaf.Core.Messages.Message;

import java.util.function.Consumer;

public class HandleReceivedMessage implements Task {
    private final Message msg;
    private final Consumer<Message> handler;
    public static final Task.Type taskType = () -> Type.TaskType.HANDLE_RECEIVED_MESSAGE;
    public HandleReceivedMessage(Message msg, Consumer<Message> handler) {
        this.msg = msg;
        this.handler = handler;
    }
    public Message getMessage() {
        return msg;
    }
    public void execute(){
        handler.accept(msg);
    }
}
