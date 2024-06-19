package org.carpooling.controllers.rest;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.mappers.CandidateMapper;
import org.carpooling.models.Candidate;
import org.carpooling.models.User;
import org.carpooling.security.AuthenticationManager;
import org.carpooling.services.contracts.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/travels/{travelId}/candidates")
public class CandidateController {
    private final AuthenticationManager authManager;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;

    @Autowired
    public CandidateController(AuthenticationManager authManager,
                               CandidateService candidateService,
                               CandidateMapper candidateMapper) {
        this.authManager = authManager;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
    }

    @PostMapping()
    public ResponseEntity<Candidate> applyForTravel(@RequestHeader HttpHeaders headers,
                                                    @PathVariable int travelId) {
        try {
            User authUser = authManager.fetchUser(headers);
            Candidate candidate = candidateMapper.toObj(authUser, travelId);
            candidateService.apply(authUser, candidate, travelId);
            return ResponseEntity.ok().body(candidate);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthenticatedRequestException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> resignFromTravel(@RequestHeader HttpHeaders headers,
                                                 @PathVariable int travelId,
                                                 @PathVariable int candidateId) {
        try {
            User authUser = authManager.fetchUser(headers);
            candidateService.resign(authUser, travelId, candidateId);
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