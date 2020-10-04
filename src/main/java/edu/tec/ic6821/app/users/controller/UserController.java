package edu.tec.ic6821.app.users.controller;

import edu.tec.ic6821.app.errors.ErrorDto;
import edu.tec.ic6821.app.security.dto.CredentialsDto;
import edu.tec.ic6821.app.users.model.User;
import edu.tec.ic6821.app.users.dto.UserDto;
import edu.tec.ic6821.app.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/enrollment")
    public ResponseEntity<?> signup(@Valid @RequestBody CredentialsDto credentialsDto) {
        try {
            Optional<User> optionalUser = userService.create(
                    credentialsDto.getUsername(),
                    credentialsDto.getPassword());

            return optionalUser.<ResponseEntity<?>>map(
                    (user) -> new ResponseEntity<>(
                            UserDto.from(user),
                            HttpStatus.CREATED)
            ).orElseGet(
                    () -> new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorDto.from(e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
