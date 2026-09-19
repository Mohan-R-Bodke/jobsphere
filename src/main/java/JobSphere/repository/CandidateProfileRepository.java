package JobSphere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import JobSphere.entity.CandidateProfile;
import JobSphere.entity.User;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {

    Optional<CandidateProfile> findByUser(User user);

    boolean existsByUser(User user);

    void deleteByUser(User user);
}