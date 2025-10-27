package org.hynaf.Core.Task;

public interface Task {
    interface Type {
        public enum TaskType {
            SEND_MESSAGE, EXECUTE_INTERNALLY, HANDLE_RECEIVED_MESSAGE
        }
        TaskType get();
    }
    void execute();
}
