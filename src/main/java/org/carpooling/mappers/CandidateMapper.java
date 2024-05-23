package org.carpooling.mappers;

import org.carpooling.models.Candidate;
import org.carpooling.models.User;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    public CandidateMapper (){

    }

    public Candidate toObj(User user, int travelId) {
        Candidate candidate = new Candidate();
        candidate.setUserId(user.getId());
        candidate.setTravelId(travelId);
        candidate.setUsername(user.getUsername());
        return candidate;
    }
}
