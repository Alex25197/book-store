package com.ap.bookstore.controller;

import com.ap.bookstore.model.User;
import com.ap.bookstore.model.UserDTO;
import com.ap.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "User management APIs")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided information"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(UserDTO.fromUser(userService.createUser(user)));
    }
    
    @Operation(
        summary = "Get a user by ID",
        description = "Returns a user based on the ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
        @Parameter(description = "ID of the user to retrieve") @PathVariable Long id
    ) {
        return ResponseEntity.ok(UserDTO.fromUser(userService.getUserById(id)));
    }
    
    @Operation(
        summary = "Get all users",
        description = "Returns a list of all users"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
            .map(UserDTO::fromUser)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @Operation(
        summary = "Delete a user",
        description = "Deletes a user based on the ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
        @Parameter(description = "ID of the user to delete") @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
} 