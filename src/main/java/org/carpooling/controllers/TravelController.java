package org.carpooling.controllers;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.mappers.TravelMapper;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.TravelDto;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    //todo implement pagination
    @GetMapping
    public ResponseEntity<List<Travel>> getAllTravels(@RequestHeader HttpHeaders headers,
                                                      @RequestParam(required = false) String startLocation,
                                                      @RequestParam(required = false) String endLocation,
                                                      @RequestParam(required = false) String driver,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                      LocalDateTime departAfter,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                      LocalDateTime departBefore,
                                                      @RequestParam(required = false) Integer freeSpots
    ) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            TravelFilterOptions travelFilter = new TravelFilterOptions(
                    startLocation, endLocation, driver, departAfter, departBefore, freeSpots
            );
            List<Travel> result = travelService.getAll(authenticatedUser, travelFilter);
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

    //todo check comment logic
    @PostMapping
    public ResponseEntity<Travel> createTravel(@RequestHeader HttpHeaders headers,
                                               @RequestBody TravelDto dto) {
        try {
            User authUser = authManager.fetchUser(headers);
            Travel travel = travelMapper.toObj(authUser, dto);
            travelService.create(authUser, travel);
            return ResponseEntity.ok().body(travel);
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> completeTravel(@RequestHeader HttpHeaders headers,
                                               @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.complete(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelTravel(@RequestHeader HttpHeaders headers,
                                             @PathVariable int id) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.cancel(authUser, id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

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

    @PutMapping("/{travelId}/approve/{candidateId}")
    public ResponseEntity<Void> approveCandidate(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int candidateId) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.approve(authUser, travelId, candidateId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{travelId}/approve/{candidateId}")
    public ResponseEntity<Void> declineCandidate(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int candidateId) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.decline(authUser, travelId, candidateId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<Void> deleteTravel(@RequestHeader HttpHeaders headers,
                                             @PathVariable int travelId) {
        try {
            User authUser = authManager.fetchUser(headers);
            travelService.delete(authUser, travelId);
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
