package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.TravelRepository;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.carpooling.helpers.constants.ModelNames.TRAVEL;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.ID;

@Service
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getAll(User authenticatedUser, TravelFilterOptions filter) {
        return null;
    }

    @Override
    public Travel getById(int travelId) {
        return travelRepository.findById(travelId).orElseThrow(
                () -> new EntityNotFoundException(TRAVEL.toString(), ID.toString(), String.valueOf(travelId)));
    }
    //todo configure webClient, client layer for both requests and responses
    @Override
    public Travel create(User creator, Travel travel) {
        return null;
    }

    @Override
    public Travel complete(User authenticatedUser, int travelId) {
        Travel toBeMarkedComplete = getById(travelId);
        TravelValidator.isStatusActive(toBeMarkedComplete);
        toBeMarkedComplete.setStatus(TravelStatus.COMPLETED);
        travelRepository.save(toBeMarkedComplete);
        return toBeMarkedComplete;
    }

    @Override
    public Travel cancel(User authenticatedUser, int travelId) {
        Travel toBeMarkedCancelled = getById(travelId);
        TravelValidator.isUserCreatorOfTravel
                (authenticatedUser, toBeMarkedCancelled.getCreator());
        TravelValidator.isStatusCancelable(toBeMarkedCancelled);
        toBeMarkedCancelled.setStatus(TravelStatus.CANCELLED);
        return toBeMarkedCancelled;
    }

    @Override
    public Travel apply(User authenticatedUser, int travelId) {
        return null;
    }

    @Override
    public Travel resign(User authenticatedUser, int travelId) {
        return null;
    }

    @Override
    public Travel approve(User authenticatedUser, int travelId, int candidateId) {
        return null;
    }

    @Override
    public Travel decline(User authenticatedUser, int travelId, int candidateId) {
        return null;
    }

    @Override
    public void delete(User authenticatedUser, int travelId) {

    }
}
