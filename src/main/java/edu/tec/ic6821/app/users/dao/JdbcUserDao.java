package edu.tec.ic6821.app.users.dao;

import edu.tec.ic6821.app.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Component
public class JdbcUserDao extends JdbcDaoSupport implements UserDao {

    @Autowired
    public JdbcUserDao(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public User create(User user) {
        String sql = "insert into user (username, password) values (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, holder);

        user.setId(holder.getKey().longValue());

        return user;    }

    @Override
    public Boolean existsByUsername(String username) {
        String sql = "select count(id) from user where username = ?";

        Long count = getJdbcTemplate().queryForObject(sql,
                new Object[]{username},
                Long.class);

        return count >= 1;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "select * from user where username = ?";

        try {
            User user = getJdbcTemplate().queryForObject(sql,
                    new Object[]{username},
                    new UserMapper());

            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
