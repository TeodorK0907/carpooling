package org.carpooling.services.contracts;

import org.carpooling.models.Candidate;
import org.carpooling.models.User;

public interface CandidateService {
    Candidate create(User user);

    Candidate getByUserId(int userId);

    void delete(Candidate candidate);
}
