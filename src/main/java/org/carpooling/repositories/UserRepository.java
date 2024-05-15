package org.carpooling.repositories;

import org.carpooling.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u "
            + "where (:username= '' or u.username like :username) "
            + "and (:email='' or u.email like :email) "
            + "and (:phoneNumber='' or u.phoneNumber like :phoneNumber)"
            + "and u.archived = false")
    Page<User> findAllWithFilter(@Param("username") String username,
                              @Param("email") String email,
                              @Param("phoneNumber") String phoneNumber,
                              Pageable page);
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
