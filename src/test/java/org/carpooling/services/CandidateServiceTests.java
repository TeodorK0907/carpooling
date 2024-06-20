package org.carpooling.services;

import jakarta.persistence.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.models.Candidate;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.CandidateRepository;
import org.carpooling.services.contracts.TravelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.carpooling.MockHelpers.*;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTests {
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private TravelService travelService;
    @InjectMocks
    private CandidateServiceImpl candidateService;

    private User user;
    private Travel travel;
    private Candidate candidate;

    @BeforeEach
    void setUpEntities() {
        user = createMockUser();
        travel = createMockTravel();
        candidate = createMockCandidate();
    }

    @Test
    void apply_Should_CreateCandidate_When_UserIsNotBlocked() {
        //Arrange
        Mockito
                .when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Candidate toApply = null;
        //Act
        toApply = candidateService.apply(user, candidate, Mockito.anyInt());

        //Assert
        Assertions.assertEquals(toApply, candidate);
    }

    @Test
    void apply_Should_ContactCandidateRepo_When_UserIsNotBlocked() {
        //Arrange
        Mockito
                .when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Candidate toApply = null;
        //Act
        toApply = candidateService.apply(user, candidate, Mockito.anyInt());

        //Assert
        Mockito.verify(candidateRepository,
                        Mockito.times(1))
                .save(candidate);
    }

    @Test
    void apply_Should_HaveTravelEntity_Add_Candidate_ToItsCollection_When_TravelIsSaved() {
        //Arrange
        Mockito
                .when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Candidate toApply = null;
        //Act
        toApply = candidateService.apply(user, candidate, Mockito.anyInt());

        //Assert
        Assertions.assertEquals(1, travel.getCandidates().size());
    }

    @Test
    void apply_Should_ContactTravelService_When_CandidateAddedToTravel() {
        //Arrange
        Mockito
                .when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);

        //Act
        candidateService.apply(user, candidate, Mockito.anyInt());

        //Assert
        Mockito.verify(travelService,
                        Mockito.times(1))
                .update(travel);
    }

    @Test
    void apply_Should_ThrowUnauthorizedOperationException_When_UserIsBlocked() {
        //Arrange
        user.setBlocked(true);

        //Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> candidateService.apply(user, candidate, 5));
    }

    @Test
    void getById_Should_Return_Candidate_When_CandidateExists() {
        //Arrange
        Mockito.when(candidateRepository.findById(Mockito.anyInt())).
                thenReturn(Optional.ofNullable(candidate));
        Candidate mockCandidate = null;

        //Act
        mockCandidate = candidateService.getById(Mockito.anyInt());

        //Assert
        Assertions.assertEquals(mockCandidate, candidate);
    }

    @Test
    void getById_Should_ThrowEntityNotFoundExceptions_When_CandidateDoesNotExist() {
        //Arrange
        Mockito.when(candidateRepository.findById(Mockito.anyInt()))
                .thenThrow(EntityNotFoundException.class);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> candidateService.getById(4));
    }


    @Test
    void getByUserIdAndTravelId_Should_Return_Candidate_When_CandidateExists() {
        //Arrange
        Mockito.when(candidateRepository.findByUserIdAndTravelId(Mockito.anyInt(), Mockito.anyInt())).
                thenReturn(Optional.ofNullable(candidate));
        Candidate mockCandidate = null;

        //Act
        mockCandidate = candidateService.getByUserIdAndTravelId(Mockito.anyInt(), Mockito.anyInt());

        //Assert
        Assertions.assertEquals(mockCandidate, candidate);
    }

    @Test
    void getByUserIdAndTravelId_Should_ThrowEntityNotFoundExceptions_When_CandidateDoesNotExist() {
        //Arrange
        Mockito.when(candidateRepository.findByUserIdAndTravelId(Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(EntityNotFoundException.class);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> candidateService.getByUserIdAndTravelId(4, 5));
    }

    @Test
    void resign_Should_ContactCandidateRepository_When_UserIsCandidate() {
        //Arrange
        Mockito.when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Mockito.when(candidateRepository.findById(candidate.getId()))
                .thenReturn(Optional.ofNullable(candidate));
        //Act
        candidateService.resign(user, travel.getId(), candidate.getId());

        //Assert
        Assertions.assertEquals(0, travel.getCandidates().size());
    }

    @Test
    void resign_Should_ContactTravelService_When_CandidateRemovedFromTravel() {
        //Arrange
        Mockito.when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Mockito.when(candidateRepository.findById(candidate.getId()))
                .thenReturn(Optional.ofNullable(candidate));
        //Act
        candidateService.resign(user, travel.getId(), candidate.getId());

        //Assert
        Mockito.verify(travelService, Mockito.times(1))
                .update(travel);
    }

    @Test
    void resign_Should_CandidateRepository_When_TravelUpdated() {
        //Arrange
        Mockito.when(travelService.getById(Mockito.anyInt()))
                .thenReturn(travel);
        Mockito.when(candidateRepository.findById(candidate.getId()))
                .thenReturn(Optional.ofNullable(candidate));
        //Act
        candidateService.resign(user, travel.getId(), candidate.getId());

        //Assert
        Mockito.verify(candidateRepository, Mockito.times(1))
                .delete(candidate);
    }

    @Test
    void resign_Should_Throw_UnauthorizedOperationException_When_UserIsNotCandidate() {
        //Arrange
        Mockito.when(candidateRepository.findById(candidate.getId()))
                .thenReturn(Optional.ofNullable(candidate));
        user.setId(5);

        //Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> candidateService.resign(user, travel.getId(), candidate.getId()));
    }

    @Test
    void delete_Should_ContactCandidateRepisotory_When_CandidateIsBeingDeleted() {
        // Arrange & Act
        candidateService.delete(candidate);

        //Assert
        //Assert
        Mockito.verify(candidateRepository, Mockito.times(1))
                .delete(candidate);
    }

}
