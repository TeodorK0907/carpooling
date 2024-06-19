package org.carpooling.controllers.rest;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.models.User;
import org.carpooling.services.contracts.PublicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {

    private final PublicService service;

    public PublicController(PublicService service) {
        this.service = service;
    }

    @GetMapping("/topTenDrivers")
    ResponseEntity<List<User>> getTopTenDrivers() {
        try {
            List<User> result = service.getTopTenDrivers();
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping ("/topTenPassengers")
    ResponseEntity<List<User>> getTopTenPassengers() {
        try {
            List<User> result = service.getTopTenPassengers();
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}