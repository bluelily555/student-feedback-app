package com.project.feedback.infra.outgoing.adapter;

import com.project.feedback.infra.outgoing.port.TaskPort;
import com.project.feedback.infra.outgoing.repository.TaskRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskAdapter implements TaskPort {
    private final TaskRepository taskRepository;

    public TaskAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


}
