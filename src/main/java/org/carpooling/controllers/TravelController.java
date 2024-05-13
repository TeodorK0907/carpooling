package org.carpooling.controllers;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelService travelService;
    private final AuthenticationManager authManager;
    private final TravelMapper travelMapper;

    @Autowired
    public TravelController(TravelService travelService,
                            AuthenticationManager authManager,
                            TravelMapper travelMapper) {
        this.travelService = travelService;
        this.authManager = authManager;
        this.travelMapper = travelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Travel>> getAllTravels(@RequestHeader HttpHeaders headers) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            //todo add travelFilterOptions
            List<Travel> result = travelService.getAll(authenticatedUser, );
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Travel> getTravelById(@RequestHeader HttpHeaders headers,
                                                @PathVariable int id) {
        try {
            authManager.fetchUser(headers);
            Travel travel = travelService.getById(id);
            return ResponseEntity.ok().body(travel);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    //todo create TravelDto
    //todo create TravelMapper
    // todo
    @GetMapping
    public ResponseEntity<Travel> createTravel(@RequestHeader HttpHeaders headers,
                                               @RequestBody TravelDto dto) {
        try {
            User authUser = authManager.fetchUser(headers);
            Travel travel = travelMapper.toObj(dto);
            travelService.create(authUser, travel);
            return ResponseEntity.ok().body(travel);
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    //todo createMarkAsComplete method in travelService
    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> completeTravel(@RequestHeader HttpHeaders headers,
                                               @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.markAsComplete(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    //todo create MarkAsCanceled method in travelService
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelTravel(@RequestHeader HttpHeaders headers,
                                             @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.markAsCanceled(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    //todo create ApplyForTravel method in travelService
    @PutMapping("{id}/apply")
    public ResponseEntity<Void> applyForTravel(@RequestHeader HttpHeaders headers,
                                               @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.apply(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    //todo create CancelParticipation method in travelService
    @PutMapping("{id}/apply")
    public ResponseEntity<Void> resignFromTravel(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.resign(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    //todo create approvePassenger method in travelService

    @PutMapping("/{travelId}/approve/{passengerId}")
    public ResponseEntity<Void> approvePassenger(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int passengerId) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.approve(authUser, travelId, passengerId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    //todo create declinePassenger method in travelService

    @PutMapping("/{travelId}/approve/{passengerId}")
    public ResponseEntity<Void> declinePassenger(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int passengerId) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.decline(authUser, travelId, passengerId);
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
