package org.carpooling.services.contracts;

import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.springframework.data.domain.Page;


public interface TravelService {
    Page<Travel> getAll(User authenticatedUser, TravelFilterOptions filter);
    Page<Travel> getAllPlanned(User authenticatedUser, TravelFilterOptions filter);

    Travel getById(int travelId);

    Travel create(User creator, Travel travel, String commentContent);

    void update(Travel travel);

    Travel complete(User authenticatedUser, int travelId);

    Travel cancel(User authenticatedUser, int travelId);

    void delete(User authenticatedUser, int travelId);
}
