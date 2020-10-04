package edu.tec.ic6821.app.tasks;

import edu.tec.ic6821.app.tasks.dao.TaskDao;
import edu.tec.ic6821.app.tasks.model.Task;
import edu.tec.ic6821.app.tasks.service.TaskService;
import edu.tec.ic6821.app.tasks.service.TaskServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Date;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TaskServiceTests {

	@TestConfiguration
    static class TaskServiceTestsConfiguration {
        
        @MockBean
        TaskDao taskDao;

        @Bean
        public TaskService taskService() {
            return new TaskServiceImpl(taskDao);
        }
    }

        @Autowired
    TaskService taskService;

    @Autowired
    TaskDao taskDao;

    @Before
    public void setUp() {
        Mockito.when(taskDao.existsById(2L)).thenReturn(true);
        Mockito.when(taskDao.existsById(4L)).thenReturn(false);
        Mockito.when(taskDao.create(ArgumentMatchers.any(Task.class)))
                .thenAnswer(invocation -> {
                   Task createdTask = invocation.getArgument(0);
                   createdTask.setId(42L);
                   return createdTask;
                });
    }

    @Test
    public void givenNewTask_whenCreate_thenReturnNewTask() {
        
        String dueDateS = "2012-10-25";
        Date dueDate = new Date();


        try {

            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateS);

        }
        catch (Exception e) {
         //The handling for the code
        }

        // given
        Long taskListId = 2L;
        boolean taskDone = false;
        String title = "Comprar queso";
        String description = "Queso turrialba 200 gramos";
        
        
        // when
        Optional<Task> result = taskService.create(taskListId, taskDone, title, description, dueDate);

        // then
        assertThat(result).isNotEmpty();

        Task newTask = result.get();
        assertThat(newTask).hasFieldOrPropertyWithValue("id", 42L);

    }

    @Test
    public void returnEmptyWhenTaskDoesNotExist() {
        
        String dueDateS = "2012-10-25";
        Date dueDate = new Date();


        try {

            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateS);

        }
        catch (Exception e) {
         //The handling for the code
        }

        // given
        Long id = 4L;
        Long taskListId = 2L;
        boolean taskDone = false;
        String title = "Comprar queso";
        String description = "Queso Manchego 200 gramos";
        
        
        // when
		Boolean result = taskService.update(id, taskListId, taskDone, title, description, dueDate);

        // then
        assertThat(result).isFalse();
    }
 
}