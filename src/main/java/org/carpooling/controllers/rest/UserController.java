package org.carpooling.controllers.rest;

import org.carpooling.mappers.UserMapper;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.UpdateUserDto;
import org.carpooling.models.input_dto.UserDto;
import org.carpooling.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        User userToCreate = userMapper.toObj(dto);
        userService.create(userToCreate);
        return ResponseEntity.ok().body(userToCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id,
                                           @RequestBody UpdateUserDto dto) {
        User userToUpdate = userMapper.toObj(id, dto);
        userService.update(userToUpdate);
        return ResponseEntity.ok().body(userToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
