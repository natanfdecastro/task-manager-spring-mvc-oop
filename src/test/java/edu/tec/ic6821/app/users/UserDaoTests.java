package edu.tec.ic6821.app.users;

import edu.tec.ic6821.app.users.dao.UserDao;
import edu.tec.ic6821.app.users.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void givenUser_whenCreate_thenReturnUserWithGeneratedId() {
        // given
        User user = new User("someuser", "123queso");

        // when
        User persistedUser = userDao.create(user);

        // then
        assertThat(persistedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(persistedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(persistedUser.getId()).isNotNull();
    }

    @Test
    public void givenExistingUsername_whenExistsByUsername_thenReturnTrue() {
        // given
        String username = "user" + new Date().getTime();
        User user = userDao.create(new User(username, "123queso"));

        // when
        Boolean exists = userDao.existsByUsername(username);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    public void givenNonExistingUsername_whenExistsByUsername_thenReturnFalse() {
        // given
        String username = "thisUsernameIsNotInTheDatabase";

        // when
        Boolean exists = userDao.existsByUsername(username);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    public void givenExistingUser_whenFindByUsername_thenReturnUser() {
        // given
        String username = "user" + new Date().getTime();
        String password = "123queso";
        User user = userDao.create(new User(username, password));

        // when
        Optional<User> found = userDao.findByUsername(username);

        assertThat(found).hasValue(user);
    }

    @Test
    public void givenNonExistingUsername_whenFindByUsername_thenReturnEmpty() {
        // given
        String username = "thisUsernameIsNotInTheDatabase";

        // when
        Optional<User> found = userDao.findByUsername(username);

        assertThat(found).isEmpty();
    }
}
