package org.hynaf.Core.Task;

public interface Task<T> {
    public enum TaskType {
        SEND_MESSAGE, EXECUTE_INTERNALLY, HANDLE_RECEIVED_MESSAGE
    }
    public TaskType getType();
    T execute();
}
