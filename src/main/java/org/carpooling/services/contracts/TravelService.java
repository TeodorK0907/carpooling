package org.carpooling.services.contracts;

import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.springframework.data.domain.Page;


public interface TravelService {
    Page<Travel> getAll(User authenticatedUser, TravelFilterOptions filter);

    Travel getById(int travelId);

    Travel create(User creator, Travel travel);

    Travel complete(User authenticatedUser, int travelId);

    Travel cancel(User authenticatedUser, int travelId);

    Travel apply(User authenticatedUser, int travelId);

    Travel resign(User authenticatedUser, int travelId);

    Travel approve(User authenticatedUser, int travelId, int candidateId);

    Travel decline(User authenticatedUser, int travelId, int candidateId);

    void delete(User authenticatedUser, int travelId);
}
