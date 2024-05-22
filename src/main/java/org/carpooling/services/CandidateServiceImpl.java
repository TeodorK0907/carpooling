package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
import org.carpooling.models.Candidate;
import org.carpooling.models.User;
import org.carpooling.repositories.CandidateRepository;
import org.carpooling.services.contracts.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate create(User user) {
        Candidate candidate = new Candidate();
        candidate.setUserId(user.getId());
        candidate.setUsername(user.getUsername());
        return candidate;
    }

    @Override
    public Candidate getByUserId(int userId) {
       return candidateRepository.findByUserId(userId)
               .orElseThrow(() -> new EntityNotFoundException(
                       ModelNames.CANDIDATE.toString(),
                       CandidateAttributes.USER_ID.toString(),
                       String.valueOf(userId)
               ));
    }

    @Override
    public void delete(Candidate candidate) {

    }
}
