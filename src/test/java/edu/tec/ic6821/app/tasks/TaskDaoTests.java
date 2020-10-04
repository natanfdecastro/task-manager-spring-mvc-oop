package edu.tec.ic6821.app.tasks;

import edu.tec.ic6821.app.tasks.dao.TaskDao;
import edu.tec.ic6821.app.tasks.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDaoTests {

    @Autowired
    private TaskDao taskDao;

    @Test
    public void givenTask_whenCreate_thenReturnTaskWithGeneratedId() {

        String dueDateS = "2012-10-25";
        Date dueDate = new Date();


        try {

            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateS);

        }
        catch (Exception e) {
         //The handling for the code
        }

        // given
        Task task = new Task(4L, false, "Comprar queso", "Queso Turrialba 200 gramos", dueDate);

        // when
        Task persistedTask = taskDao.create(task);

        // then
        assertThat(persistedTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(persistedTask.getId()).isNotNull();
    }
}