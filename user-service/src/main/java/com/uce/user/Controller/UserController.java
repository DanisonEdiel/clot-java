package com.uce.user.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.uce.user.Model.User;
import com.uce.user.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Api Rest for brands use Swagger 3 - User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "This endpoint is used for register a new user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful, please check your email to confirm.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint is used to find a user")
    public ResponseEntity<User> getUser(@PathVariable ObjectId id) {
        return ResponseEntity.ok(userService.findByUser(id));
    }

    @GetMapping
    @Operation(summary = "This endpoint is used to all users")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getAll());
    }

}
