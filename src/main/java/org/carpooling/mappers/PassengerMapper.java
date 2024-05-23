package org.carpooling.mappers;

import org.carpooling.models.Candidate;
import org.carpooling.models.Passenger;
import org.carpooling.services.contracts.CandidateService;
import org.carpooling.services.contracts.PassengerService;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    private final CandidateService candidateService;
    private final PassengerService passengerService;

    public PassengerMapper(CandidateService candidateService,
                           PassengerService passengerService) {
        this.candidateService = candidateService;
        this.passengerService = passengerService;
    }

    public Passenger toObj(int candidateId) {
       Candidate candidateToApprove = candidateService.getById(candidateId);
       Passenger passenger = new Passenger();
        passenger.setUserId(candidateToApprove.getUserId());
       passenger.setTravelId(candidateToApprove.getTravelId());
       passenger.setUsername(candidateToApprove.getUsername());
       passenger.setGivenRating(false);
       passenger.setGaveRating(false);
       return passenger;
    }
}
