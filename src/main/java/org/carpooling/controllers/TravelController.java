package org.carpooling.controllers;

import jakarta.validation.Valid;
import org.carpooling.exceptions.*;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.mappers.TravelMapper;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.TravelDto;
import org.carpooling.models.output_dto.TravelOutputDto;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
    public ResponseEntity<Page<Travel>> getAllTravels(@RequestHeader HttpHeaders headers,
                                                      @RequestParam(required = false) String startLocation,
                                                      @RequestParam(required = false) String endLocation,
                                                      @RequestParam(required = false) String driver,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                      LocalDateTime departAfter,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                      LocalDateTime departBefore,
                                                      @RequestParam(required = false) Integer freeSpots,
                                                      @RequestParam(required = false) Integer pageNum,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String sortBy,
                                                      @RequestParam(required = false) String orderBy
    ) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            TravelFilterOptions travelFilter = new TravelFilterOptions(
                    startLocation, endLocation, driver, departAfter, departBefore,
                    freeSpots, pageNum, pageSize, sortBy, orderBy
            );
            Page<Travel> result = travelService.getAll(authenticatedUser, travelFilter);
            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelOutputDto> getTravelById(@RequestHeader HttpHeaders headers,
                                                @PathVariable int id) {
        try {
            authManager.fetchUser(headers);
            Travel travel = travelService.getById(id);
            TravelOutputDto output = travelMapper.toDto(travel);
            return ResponseEntity.ok().body(output);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<TravelOutputDto> createTravel(@RequestHeader HttpHeaders headers,
                                               @Valid @RequestBody TravelDto dto) {
        try {
            User authUser = authManager.fetchUser(headers);
            Travel travel = travelMapper.toObj(authUser, dto);
            travelService.create(authUser, travel, dto.getComment());
            TravelOutputDto output = travelMapper.toDto(travel);
            return ResponseEntity.ok().body(output);
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnsuccessfulResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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
        }  catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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