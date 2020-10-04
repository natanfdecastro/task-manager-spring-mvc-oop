package edu.tec.ic6821.app.security.controller;

import edu.tec.ic6821.app.errors.ErrorDto;
import edu.tec.ic6821.app.security.service.AuthenticationService;
import edu.tec.ic6821.app.security.dto.CredentialsDto;
import edu.tec.ic6821.app.security.dto.JwtDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody CredentialsDto credentialsDto) {
        try {
            logger.info("[authentication] Attempting to authenticate {}", credentialsDto.getUsername());
            String jwt = authenticationService.authenticate(
                    credentialsDto.getUsername(),
                    credentialsDto.getPassword());

            logger.info("[authentication] Authentication successful {}", credentialsDto.getUsername());
            return ResponseEntity.ok(new JwtDto(jwt));

        } catch (BadCredentialsException e) {
            logger.error("[authenticate] Authentication failed: {} ", e.getMessage());
            return new ResponseEntity<>(ErrorDto.from(e), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("[authenticate] Unexpected error ", e);
            return new ResponseEntity<>(ErrorDto.from(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
