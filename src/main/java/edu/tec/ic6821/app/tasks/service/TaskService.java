package edu.tec.ic6821.app.tasks.service;

import edu.tec.ic6821.app.tasks.model.Task;

import java.util.Optional;
import java.util.Date;

public interface TaskService {
	
	Optional<Task> create(Long taskListId, boolean taskDone, String title, String description, Date dueDate);
	Boolean update(Long id, Long taskListId, boolean taskDone, String title, String description, Date dueDate);
} 