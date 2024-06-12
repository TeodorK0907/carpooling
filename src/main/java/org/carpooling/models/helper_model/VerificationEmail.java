package org.carpooling.models.helper_model;

import jakarta.persistence.*;
import org.carpooling.helpers.constants.VerificationStatus;

import java.util.Objects;
@Entity
@Table(name = "verification_email", schema = "rose-valley-travel")
public class VerificationEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id")
    private String mailId;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private VerificationStatus status;

    public VerificationEmail() {

    }

    public VerificationEmail(String mailId, String email, VerificationStatus status) {
        this.mailId = mailId;
        this.email = email;
        this.status = status;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationEmail that)) return false;
        return Objects.equals(mailId, that.mailId)
                && Objects.equals(email, that.email)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailId, email, status);
    }
}
