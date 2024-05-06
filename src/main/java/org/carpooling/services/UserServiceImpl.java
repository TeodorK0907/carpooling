package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("user", "id", String.valueOf(userId)));
    }

    @Override
    public User create(User user) {

        userRepository.save(user);
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(int userId) {

    }
}
