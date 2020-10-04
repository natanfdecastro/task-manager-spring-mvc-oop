
package edu.tec.ic6821.app.tasks.dao;

import edu.tec.ic6821.app.tasks.model.Task;

import java.util.Optional;

public interface TaskDao {
    
    Task create(Task task);
    Boolean update(Task task);
    Boolean existsById(Long id);
}