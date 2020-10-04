package edu.tec.ic6821.app.users;

import edu.tec.ic6821.app.users.dao.UserDao;
import edu.tec.ic6821.app.users.model.User;
import edu.tec.ic6821.app.users.service.UserService;
import edu.tec.ic6821.app.users.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceTests {

    @TestConfiguration
    static class UserServiceTestsConfiguration {
        @MockBean
        UserDao userDao;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserService userService() {
            return new UserServiceImpl(userDao, passwordEncoder());
        }
    }

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        Mockito.when(userDao.existsByUsername("someexistinguser")).thenReturn(true);
        Mockito.when(userDao.existsByUsername("somenewuser")).thenReturn(false);
        Mockito.when(userDao.create(ArgumentMatchers.any(User.class)))
                .thenAnswer(invocation -> {
                   User createdUser = invocation.getArgument(0);
                   createdUser.setId(42L);
                   return createdUser;
                });
    }

    @Test
    public void givenExistingUsername_whenCreate_thenReturnEmpty() {
        // given
        String username = "someexistinguser";
        String password = "123queso";

        // when
        Optional<User> result = userService.create(username, password);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    public void givenNewUsername_whenCreate_thenReturnNewUser() {
        // given
        String username = "somenewuser";
        String password = "123queso";

        // when
        Optional<User> result = userService.create(username, password);

        // then
        assertThat(result).isNotEmpty();

        User newUser = result.get();
        assertThat(newUser).hasFieldOrPropertyWithValue("id", 42L);
        assertThat(passwordEncoder.matches(password, newUser.getPassword())).isTrue();
    }
}
