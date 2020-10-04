package edu.tec.ic6821.app.tasks.dao;

import edu.tec.ic6821.app.tasks.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.util.Optional;

import java.util.logging.Level; 
import java.util.logging.Logger; 

@Component
public class JdbcTaskDao extends JdbcDaoSupport implements TaskDao {

	@Autowired
	public JdbcTaskDao(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public Task create(Task task) {
		String sql = "insert into task (task_list_id, taskdone, title, description, duedate) values (?, ?, ?, ?, ?)";

		KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, task.getTaskListId());
            ps.setBoolean(2, task.getTaskDone());
            ps.setString(3, task.getTitle());
            ps.setString(4, task.getDescription());
            ps.setDate(5, new java.sql.Date(task.getDueDate().getTime()));
            return ps;
        }, holder);

        Logger logger = Logger.getLogger(JdbcTaskDao.class.getName()); 

        logger.log(Level.INFO, "Task CREATED"); 

        task.setId(holder.getKey().longValue());

        return task;    
	}

    @Override
    public Boolean update(Task task) {

        Boolean updatedRegistersStatus = true;

        String sql = "update task set task_list_id = ?, taskdone = ?, title = ?, description = ?, duedate = ? where id = ?";
        
        getJdbcTemplate().update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, task.getTaskListId());
            ps.setBoolean(2, task.getTaskDone());
            ps.setString(3, task.getTitle());
            ps.setString(4, task.getDescription());
            ps.setDate(5, new java.sql.Date(task.getDueDate().getTime()));
            ps.setLong(6, task.getId());

            // Checks the amount of updated registers in database
            int updatedRegisters = ps.executeUpdate();

            Logger logger = Logger.getLogger(JdbcTaskDao.class.getName()); 

            logger.log(Level.INFO, "UPDATED REGISTERS: " + updatedRegisters); 

            return ps;
        });

        return true;
    }
    
    @Override
    public Boolean existsById(Long id) {
        String sql = "select count(id) from task where id = ?";

        Long count = getJdbcTemplate().queryForObject(sql,
                new Object[]{id},
                Long.class);

        return count >= 1;
    }
    
}