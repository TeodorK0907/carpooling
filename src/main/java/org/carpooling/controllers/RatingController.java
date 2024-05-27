package org.carpooling.controllers;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.DuplicateRequestException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.models.Rating;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.RatingDto;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/travels/{travelId}/feedbacks")
public class RatingController {
    private final RatingService ratingService;
    private final AuthenticationManager authManager;
    @Autowired
    public RatingController(RatingService ratingService,
                            AuthenticationManager authManager) {
        this.ratingService = ratingService;
        this.authManager = authManager;
    }

    @PutMapping("/passenger/{passengerId}")
    public ResponseEntity<Rating> givePassengerRating(@RequestHeader HttpHeaders headers,
                                                      @PathVariable int travelId,
                                                      @PathVariable int passengerId,
                                                      @RequestBody RatingDto dto) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            Rating result = ratingService.givePassengerRating(
                    authenticatedUser, passengerId, travelId, dto.getRating()
            );
            return ResponseEntity.ok().body(result);
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DuplicateRequestException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/driver")
    public ResponseEntity<Rating> giveDriverRating(@RequestHeader HttpHeaders headers,
                                                   @PathVariable int travelId,
                                                   @RequestBody RatingDto dto) {
        try {
            User authenticatedUser = authManager.fetchUser(headers);
            Rating result = ratingService.giveDriverRating(
                    authenticatedUser, travelId, dto.getRating()
            );
            return ResponseEntity.ok().body(result);
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DuplicateRequestException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}