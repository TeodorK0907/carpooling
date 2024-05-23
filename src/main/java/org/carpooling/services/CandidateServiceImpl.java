package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
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
        Travel travelToApply = travelService.getById(travelId);

        travelToApply.getCandidates().add(candidate);
        travelService.update(travelToApply);
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
    public void resign(User user, int travelId) {
        //           if (CandidateValidator.isCandidaterInTravel(travelToApply, candidate)) {
//               throw new DuplicateEntityException(
//                       ModelNames.CANDIDATE.toString(),
//                       CandidateAttributes.USER_ID.toString(),
//                       String.valueOf(candidate.getUserId())
//               );
//           }
        Candidate candidate = getByUserIdAndTravelId(user.getId(), travelId);
        Travel travel = travelService.getById(travelId);
        travel.getCandidates().remove(candidate);
        travelService.update(travel);
        candidateRepository.delete(candidate);
    }
}
