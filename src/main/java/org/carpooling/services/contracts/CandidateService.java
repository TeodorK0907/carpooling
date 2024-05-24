package org.carpooling.services.contracts;

import org.carpooling.models.Candidate;
import org.carpooling.models.User;

public interface CandidateService {
    Candidate apply(User user, Candidate candidate, int travelId);

    Candidate getById(int candidateId);

    Candidate getByUserIdAndTravelId(int userId, int travelId);

    void resign(User user, int travelId, int candidateId);

    void delete(Candidate candidate);
}
