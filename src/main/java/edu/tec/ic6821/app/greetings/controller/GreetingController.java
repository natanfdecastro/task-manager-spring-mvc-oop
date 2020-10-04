package edu.tec.ic6821.app.greetings.controller;

import edu.tec.ic6821.app.greetings.dto.GreetingDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {

    @GetMapping
    public ResponseEntity<?> greet() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String message = String.format("Hello, %s!", userDetails.getUsername());

        return new ResponseEntity<>(new GreetingDto(message), HttpStatus.OK);
    }
}
