package org.carpooling.repositories;

import org.carpooling.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByArchivedIsFalse(); 
    List<User> findAllByArchivedIsFalseAndUsernameLikeOrEmailLikeOrPhoneNumberLike(String username,
                                                                                   String email,
                                                                                   String phoneNumber);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
