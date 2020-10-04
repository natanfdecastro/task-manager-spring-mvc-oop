package edu.tec.ic6821.app.tasks.controller;

import edu.tec.ic6821.app.errors.ErrorDto;
import edu.tec.ic6821.app.security.dto.CredentialsDto;
import edu.tec.ic6821.app.tasks.model.Task;
import edu.tec.ic6821.app.tasks.dto.TaskDto;
import edu.tec.ic6821.app.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;


import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PutMapping("/taskList/{task_list_id}/task/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "task_list_id") Long taskListId,@PathVariable(value = "id") Long id, @RequestBody TaskDto taskDto) {

        try {
            Boolean updatedTaskStatus = taskService.update(
                    id,
                    taskListId,
                    taskDto.getTaskDone(),
                    taskDto.getTitle(),
                    taskDto.getDescription(),
                    taskDto.getDueDate());

            if(updatedTaskStatus)
                return new ResponseEntity<>(HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(ErrorDto.from(e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<?> addtask(@Valid @RequestBody TaskDto taskDto) {

    	try {
            Optional<Task> optionalTask = taskService.create(
                    taskDto.getTaskListId(),
                    taskDto.getTaskDone(),
                    taskDto.getTitle(),
                    taskDto.getDescription(),
                    taskDto.getDueDate());

            return optionalTask.<ResponseEntity<?>>map(
                    (task) -> new ResponseEntity<>(
                            TaskDto.from(task),
                            HttpStatus.CREATED)
            ).orElseGet(
                    () -> new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorDto.from(e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

