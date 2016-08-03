package com.javaclasses.todolistapp.storage;

import com.javaclasses.todolistapp.entities.Task;
import com.javaclasses.todolistapp.tinytypes.TaskId;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of the Repository for Task repository
 */
public class TaskRepositoryImpl extends InMemoryRepository<TaskId, Task> {

    private AtomicLong count = new AtomicLong(0);

    private static Repository<TaskId, Task> taskRepository = new TaskRepositoryImpl();

    private TaskRepositoryImpl() {}

    public static Repository<TaskId, Task> getInstance() {
        return taskRepository;
    }


    @Override
    public TaskId generateId() {
        return new TaskId(count.getAndIncrement());
    }


}
