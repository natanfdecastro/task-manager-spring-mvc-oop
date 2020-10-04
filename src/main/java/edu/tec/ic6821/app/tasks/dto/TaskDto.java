package edu.tec.ic6821.app.tasks.dto;

import edu.tec.ic6821.app.tasks.model.Task;
import java.util.Date;

public class TaskDto {

	private Long id;
	private Long taskListId;
	private boolean taskDone;
	private String title;
	private String description;
	private Date dueDate;

	public static TaskDto from(Task task) {
		return new TaskDto(task.getId(), task.getTaskListId(), task.getTaskDone(), task.getTitle(), task.getDescription(), task.getDueDate());
	}

	private TaskDto(Long id, Long taskListId, boolean taskDone, String title, String description, Date dueDate) {

		this.id = id;
		this.taskListId = taskListId;
		this.taskDone = taskDone;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public Long getTaskListId() {
		return taskListId;
	}

	public Boolean getTaskDone() {
		return taskDone;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getDueDate() {
		return dueDate;
	}
} 