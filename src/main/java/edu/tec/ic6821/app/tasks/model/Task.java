package edu.tec.ic6821.app.tasks.model;

import java.util.Objects;
import java.util.Date;

public class Task {

	private Long id;
	private Long taskListId;
	private boolean taskDone;
	private String title;
	private String description;
	private Date dueDate;

	public Task(Long id, Long taskListId, boolean taskDone, String title, String description, Date dueDate) {

		this.id = id;
		this.taskListId = taskListId;
		this.taskDone = false;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;

	}

	public Task(Long taskListId, boolean taskDone, String title, String description, Date dueDate) {

		this.taskListId = taskListId;
		this.taskDone = false;
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

	public void setId(Long id) {
        this.id = id;
    }

	public boolean getTaskDone() {
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

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", tasklistid='" + taskListId + '\'' +
				", taskdone='" + taskDone + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", duedate='" + dueDate + '\'' +
				'}';
	}

}