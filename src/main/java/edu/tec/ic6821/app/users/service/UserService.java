package edu.tec.ic6821.app.users.service;

import edu.tec.ic6821.app.users.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> create(String username, String password);
}
