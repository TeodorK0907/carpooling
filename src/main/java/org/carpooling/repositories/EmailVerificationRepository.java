package org.carpooling.repositories;

import org.carpooling.models.helper_model.VerificationEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<VerificationEmail, Long> {
    VerificationEmail findByEmail(String userEmail);
}
