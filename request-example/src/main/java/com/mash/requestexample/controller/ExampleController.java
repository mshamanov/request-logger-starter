package com.mash.requestexample.controller;

import com.mash.requestexample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Simple controller for the example purposes.
 *
 * @author Mikhail Shamanov
 */
@RestController
@RequestMapping("/api/v1")
public class ExampleController {
    @GetMapping("/greet")
    public ResponseEntity<?> greet(@RequestParam(defaultValue = "user") String name) {
        return ResponseEntity.ok("Hello, " + name + "!");
    }

    @PostMapping("/user")
    public ResponseEntity<?> user(@RequestBody User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid user: either firstName or lastName is empty");
        }

        return ResponseEntity.ok(Map.of("user", user));
    }
}
