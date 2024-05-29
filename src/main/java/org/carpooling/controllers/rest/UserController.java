package org.carpooling.controllers.rest;

import org.carpooling.exceptions.DuplicateEntityException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.model_filters.UserFilterOptions;
import org.carpooling.mappers.UserMapper;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.UpdateUserDto;
import org.carpooling.models.input_dto.UserDto;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
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
    private final AuthenticationManager authManager;

    @Autowired
    public UserController(UserService userService,
                          UserMapper userMapper,
                          AuthenticationManager authManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authManager = authManager;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestHeader HttpHeaders headers,
                                                  @RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String email,
                                                  @RequestParam(required = false) String phone_number,
                                                  @RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String sortBy,
                                                  @RequestParam(required = false) String orderBy
    ) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            UserFilterOptions filter = new UserFilterOptions(
                    username, email, phone_number, pageNum, pageSize, sortBy, orderBy);
            Page<User> result = userService.getAll(authenticatedUser, filter);
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader HttpHeaders headers,
                                            @PathVariable int id) {
        try {
            authManager.fetchUser(headers);
            User user = userService.getById(id);
            return ResponseEntity.ok().body(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
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
    public ResponseEntity<User> updateUser(@RequestHeader HttpHeaders headers,
                                           @PathVariable int id,
                                           @RequestBody UpdateUserDto dto) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            User userToUpdate = userMapper.toObj(id, dto);
            userService.update(authenticatedUser, userToUpdate);
            return ResponseEntity.ok().body(userToUpdate);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@RequestHeader HttpHeaders headers,
                                          @PathVariable int id) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            userService.block(authenticatedUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@RequestHeader HttpHeaders headers,
                                            @PathVariable int id) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            userService.unblock(authenticatedUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestHeader HttpHeaders headers,
                                           @PathVariable int id) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            userService.delete(authenticatedUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
