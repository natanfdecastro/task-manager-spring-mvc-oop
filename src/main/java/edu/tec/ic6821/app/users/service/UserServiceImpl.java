package edu.tec.ic6821.app.users.service;

import edu.tec.ic6821.app.users.model.User;
import edu.tec.ic6821.app.users.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<User> create(String username, String password) {
        Boolean userAlreadyExists = userDao.existsByUsername(username);
        if (userAlreadyExists) {
            return Optional.empty();
        } else {
            User user = new User(username, passwordEncoder.encode(password));
            return Optional.of(userDao.create(user));
        }
    }
}
