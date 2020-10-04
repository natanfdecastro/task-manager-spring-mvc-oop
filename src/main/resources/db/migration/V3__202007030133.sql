CREATE TABLE task (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  task_list_id bigint(20) NOT NULL,
  taskdone boolean NOT NULL DEFAULT FALSE,
  title varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  duedate date DEFAULT NULL,
  FOREIGN KEY (task_list_id) REFERENCES task_list(id),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2;