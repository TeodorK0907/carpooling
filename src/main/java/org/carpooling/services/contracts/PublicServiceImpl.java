package org.carpooling.services.contracts;

import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.PublicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private final PublicRepository publicRepo;

    @Autowired
    public PublicServiceImpl(PublicRepository publicRepo) {
        this.publicRepo = publicRepo;
    }

    @Override
    public List<User> getTopTenDrivers() {
        List<User> topTenDrivers = publicRepo.getTopTenDrivers();
        UserValidator.isUserListEmpty(topTenDrivers);
        return topTenDrivers;
    }

    @Override
    public List<User> getTopTenPassengers() {
        List<User> topTenPassengers = publicRepo.getTopTenPassengers();
        UserValidator.isUserListEmpty(topTenPassengers);
        return topTenPassengers;
    }
}