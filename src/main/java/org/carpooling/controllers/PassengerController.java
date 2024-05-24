package org.carpooling.controllers;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.mappers.PassengerMapper;
import org.carpooling.models.Passenger;
import org.carpooling.models.User;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/travels/{travelId}/passengers")
public class PassengerController {
    private final AuthenticationManager authManager;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerController(AuthenticationManager authManager,
                               PassengerService passengerService,
                               PassengerMapper passengerMapper) {
        this.authManager = authManager;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    @PostMapping("/{candidateId}")
    public ResponseEntity<Void> approveCandidate(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int candidateId) {
        try {
            User authUser = authManager.fetchUser(headers);
            Passenger passenger = passengerMapper.toObj(candidateId);
            passengerService.approve(authUser, passenger, travelId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<Void> removePassenger(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int passengerId) {
        try {
            User authUser = authManager.fetchUser(headers);
            passengerService.decline(authUser, passengerId, travelId);
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