CREATE TABLE task_list (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  user_id bigint(20) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2;