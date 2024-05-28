package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
import org.carpooling.helpers.errors.CandidateValidatorErrors;
import org.carpooling.helpers.validators.CandidateValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.Candidate;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.CandidateRepository;
import org.carpooling.services.contracts.CandidateService;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final TravelService travelService;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                TravelService travelService) {
        this.candidateRepository = candidateRepository;
        this.travelService = travelService;
    }

    @Override
    public Candidate apply(User user, Candidate candidate, int travelId) {
        UserValidator.isBlocked(user);
        candidateRepository.save(candidate);
        Travel toApply = travelService.getById(travelId);
        toApply.getCandidates().add(candidate);
        travelService.update(toApply);
        return candidate;
    }

    @Override
    public Candidate getById(int candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.CANDIDATE.toString(),
                        CandidateAttributes.ID.toString(),
                        String.valueOf(candidateId)
                ));
    }

    @Override
    public Candidate getByUserIdAndTravelId(int userId, int travelId) {
        return candidateRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.CANDIDATE.toString(),
                        CandidateAttributes.USER_ID.toString(),
                        String.valueOf(userId)
                ));
    }

    @Override
    public void resign(User user, int travelId, int candidateId) {
        Candidate toRemove = getById(candidateId);
        if (CandidateValidator.isUserCandidate(user, toRemove)) {
            Travel travel = travelService.getById(travelId);
            travel.getCandidates().remove(toRemove);
            travelService.update(travel);
            candidateRepository.delete(toRemove);
        }
        throw new UnauthorizedOperationException(CandidateValidatorErrors.UNAUTHORIZED.toString());
    }

    @Override
    public void delete(Candidate candidate) {
        candidateRepository.delete(candidate);
    }
}