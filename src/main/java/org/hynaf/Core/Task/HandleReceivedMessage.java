package org.hynaf.Core.Task;

import org.hynaf.Core.Messages.Message;

import java.util.function.Consumer;

public class HandleReceivedMessage implements Task<Message> {
    private final Message msg;

    @Override
    public TaskType getType(){
       return TaskType.HANDLE_RECEIVED_MESSAGE;
    }
    public HandleReceivedMessage(Message msg) {
        this.msg = msg;
    }

    @Override
    public Message execute(){
        return msg;
    }
}
