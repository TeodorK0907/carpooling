package org.carpooling.controllers.rest;

import org.carpooling.exceptions.DuplicateEntityException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.mappers.UserMapper;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.UpdateUserDto;
import org.carpooling.models.input_dto.UserDto;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> result = userService.getAll();
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            User user = userService.getById(id);
            return ResponseEntity.ok().body(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        try {
            User user = userService.getByUsername(username);
            return ResponseEntity.ok().body(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        try {
            User user = userService.getByEmail(email);
            return ResponseEntity.ok().body(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<User> getUserByPhoneNumber(@RequestParam String phone_number) {
        try {
            User user = userService.getByPhoneNumber(phone_number);
            return ResponseEntity.ok().body(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        try {
            User userToCreate = userMapper.toObj(dto);
            userService.create(userToCreate);
            return ResponseEntity.ok().body(userToCreate);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id,
                                           @RequestBody UpdateUserDto dto) {
        try {
            User userToUpdate = userMapper.toObj(id, dto);
            userService.update(userToUpdate);
            return ResponseEntity.ok().body(userToUpdate);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable int id) {
        try {
            userService.block(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable int id) {
        try {
            userService.unblock(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
