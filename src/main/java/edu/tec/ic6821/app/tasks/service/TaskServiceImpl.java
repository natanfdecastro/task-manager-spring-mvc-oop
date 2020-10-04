package edu.tec.ic6821.app.tasks.service;

import edu.tec.ic6821.app.tasks.model.Task;
import edu.tec.ic6821.app.tasks.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskDao taskDao;

	@Autowired
    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao; 
    }

	@Override
	public Optional<Task> create(Long taskListId, boolean taskDone, String title, String description, Date dueDate) {

		Task task = new Task(taskListId, taskDone, title, description, dueDate);

		return Optional.of(taskDao.create(task));

	}

	@Override
	public Boolean update(Long id, Long taskListId, boolean taskDone, String title, String description, Date dueDate) {
        

            Task task = new Task(id, taskListId, taskDone, title, description, dueDate);

            return taskDao.update(task);
    }
} 