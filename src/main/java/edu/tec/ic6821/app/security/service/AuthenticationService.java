package edu.tec.ic6821.app.security.service;

public interface AuthenticationService {
    String authenticate(String username, String password);
}
